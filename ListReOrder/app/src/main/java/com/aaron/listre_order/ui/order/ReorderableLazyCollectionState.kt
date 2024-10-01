package com.aaron.listre_order.ui.order

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.cancellation.CancellationException

@Stable
open class ReorderableLazyCollectionState<out T> internal constructor(
    private val state: LazyCollectionState<T>,
    private val scope: CoroutineScope,
    private val onMoveState: State<suspend CoroutineScope.(from: T, to: T) -> Unit>,

    /**
     * The threshold in pixels for scrolling the list when dragging an item.
     * If the dragged item is within this threshold of the top or bottom of the list, the list will scroll.
     * Must be greater than 0.
     */
    private val scrollThreshold: Float,
    private val scrollThresholdPadding: AbsolutePixelPadding,
    private val scroller: Scroller,

    private val layoutDirection: LayoutDirection,

    /**
     * Whether this is a LazyVerticalStaggeredGrid
     */
    private val lazyVerticalStaggeredGridRtlFix: Boolean = false,

    /**
     * A function that determines whether the `draggingItem` should be moved with the `item`.
     * Given their bounding rectangles, return `true` if they should be moved.
     * The default implementation is to move if the dragging item's bounding rectangle has crossed the center of the item's bounding rectangle.
     */
    private val shouldItemMove: (draggingItem: Rect, item: Rect) -> Boolean = { draggingItem, item ->
        draggingItem.contains(item.center)
    },
) {
    private val onMoveStateMutex: Mutex = Mutex()

    internal val orientation: Orientation
        get() = state.layoutInfo.orientation

    private var draggingItemKey by mutableStateOf<Any?>(null)
    private val draggingItemIndex: Int?
        get() = draggingItemLayoutInfo?.index

    /**
     * Whether any item is being dragged. This property is observable.
     */
    val isAnyItemDragging by derivedStateOf {
        draggingItemKey != null
    }

    private var draggingItemDraggedDelta by mutableStateOf(Offset.Zero)
    private var draggingItemInitialOffset by mutableStateOf(IntOffset.Zero)

    // visibleItemsInfo doesn't update immediately after onMove, draggingItemLayoutInfo.item may be outdated for a short time.
    // not a clean solution, but it works.
    private var oldDraggingItemIndex by mutableStateOf<Int?>(null)
    private var predictedDraggingItemOffset by mutableStateOf<IntOffset?>(null)

    private val draggingItemLayoutInfo: LazyCollectionItemInfo<T>?
        get() = draggingItemKey?.let { draggingItemKey ->
            state.layoutInfo.visibleItemsInfo.firstOrNull { it.key == draggingItemKey }
        }
    internal val draggingItemOffset: Offset
        get() = (draggingItemLayoutInfo?.let {
            val offset =
                if (it.index != oldDraggingItemIndex || oldDraggingItemIndex == null) {
                    oldDraggingItemIndex = null
                    predictedDraggingItemOffset = null
                    it.offset
                } else {
                    predictedDraggingItemOffset ?: it.offset
                }

            draggingItemDraggedDelta +
                    (draggingItemInitialOffset.toOffset() - offset.toOffset())
                        .reverseAxisIfNecessary()
                        .reverseAxisWithLayoutDirectionIfLazyVerticalStaggeredGridRtlFix()
        }) ?: Offset.Zero

    // the offset of the handle center from the top left of the dragging item when dragging starts
    private var draggingItemHandleOffset = Offset.Zero

    internal val reorderableKeys = HashSet<Any?>()

    internal var previousDraggingItemKey by mutableStateOf<Any?>(null)
        private set
    internal var previousDraggingItemOffset = Animatable(Offset.Zero, Offset.VectorConverter)
        private set

    private fun Offset.reverseAxisWithReverseLayoutIfNecessary() =
        when (state.layoutInfo.reverseLayout) {
            true -> reverseAxis(orientation)
            false -> this
        }

    private fun Offset.reverseAxisWithLayoutDirectionIfNecessary() = when (orientation) {
        Orientation.Vertical -> this
        Orientation.Horizontal -> reverseAxisWithLayoutDirection()
    }

    private fun Offset.reverseAxisWithLayoutDirection() = when (layoutDirection) {
        LayoutDirection.Ltr -> this
        LayoutDirection.Rtl -> reverseAxis(Orientation.Horizontal)
    }

    private fun Offset.reverseAxisWithLayoutDirectionIfLazyVerticalStaggeredGridRtlFix() =
        when (layoutDirection) {
            LayoutDirection.Ltr -> this
            LayoutDirection.Rtl -> if (lazyVerticalStaggeredGridRtlFix && orientation == Orientation.Vertical)
                reverseAxis(Orientation.Horizontal)
            else this
        }

    private fun Offset.reverseAxisIfNecessary() =
        this.reverseAxisWithReverseLayoutIfNecessary()
            .reverseAxisWithLayoutDirectionIfNecessary()

    private fun Offset.mainAxis() = getAxis(orientation)

    private fun IntOffset.mainAxis() = getAxis(orientation)

    internal suspend fun onDragStart(key: Any, handleOffset: Offset) {
        state.layoutInfo.visibleItemsInfo.firstOrNull { item ->
            item.key == key
        }?.also {
            val mainAxisOffset = it.offset.mainAxis()
            if (mainAxisOffset < 0) {
                // if item is not fully in view, scroll to it
                state.animateScrollBy(mainAxisOffset.toFloat(), spring())
            }

            draggingItemKey = key
            draggingItemInitialOffset = it.offset
            draggingItemHandleOffset = handleOffset
        }
    }

    internal fun onDragStop() {
        val previousDraggingItemInitialOffset = draggingItemLayoutInfo?.offset

        if (draggingItemIndex != null) {
            previousDraggingItemKey = draggingItemKey
            val startOffset = draggingItemOffset
            scope.launch {
                previousDraggingItemOffset.snapTo(startOffset)
                previousDraggingItemOffset.animateTo(
                    Offset.Zero,
                    spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = Offset.VisibilityThreshold
                    )
                )
                previousDraggingItemKey = null
            }
        }
        draggingItemDraggedDelta = Offset.Zero
        draggingItemKey = null
        draggingItemInitialOffset = previousDraggingItemInitialOffset ?: IntOffset.Zero
        scroller.tryStop()
        oldDraggingItemIndex = null
        predictedDraggingItemOffset = null
    }

    internal fun onDrag(offset: Offset) {
        draggingItemDraggedDelta += offset

        val draggingItem = draggingItemLayoutInfo ?: return
        // how far the dragging item is from the original position
        val dragOffset = draggingItemOffset.reverseAxisIfNecessary()
            .reverseAxisWithLayoutDirectionIfLazyVerticalStaggeredGridRtlFix()
        val startOffset = draggingItem.offset.toOffset() + dragOffset
        val endOffset = startOffset + draggingItem.size.toSize()
        val (contentStartOffset, contentEndOffset) = state.layoutInfo.getScrollAreaOffsets(
            scrollThresholdPadding
        )

        // the distance from the top or left of the list to the center of the dragging item handle
        val handleOffset =
            when (state.layoutInfo.reverseLayout ||
                    (layoutDirection == LayoutDirection.Rtl &&
                            orientation == Orientation.Horizontal)) {
                true -> endOffset - draggingItemHandleOffset
                false -> startOffset + draggingItemHandleOffset
            } + IntOffset.fromAxis(
                orientation,
                state.layoutInfo.beforeContentPadding
            ).toOffset()

        // check if the handle center is in the scroll threshold
        val distanceFromStart = (handleOffset.getAxis(orientation) - contentStartOffset)
            .coerceAtLeast(0f)
        val distanceFromEnd = (contentEndOffset - handleOffset.getAxis(orientation))
            .coerceAtLeast(0f)

        val isScrollingStarted = if (distanceFromStart < scrollThreshold) {
            scroller.start(
                Scroller.Direction.BACKWARD,
                getScrollSpeedMultiplier(distanceFromStart),
                maxScrollDistanceProvider = {
                    // distance from the start of the dragging item's stationary position to the end of the list
                    (draggingItemLayoutInfo?.let {
                        state.layoutInfo.mainAxisViewportSize -
                                it.offset.toOffset().getAxis(orientation) - 1f
                    }) ?: 0f
                },
                onScroll = {
                    moveDraggingItemToEnd(Scroller.Direction.BACKWARD)
                }
            )
        } else if (distanceFromEnd < scrollThreshold) {
            scroller.start(
                Scroller.Direction.FORWARD,
                getScrollSpeedMultiplier(distanceFromEnd),
                maxScrollDistanceProvider = {
                    // distance from the end of the dragging item's stationary position to the start of the list
                    (draggingItemLayoutInfo?.let {
                        val visibleItems = state.layoutInfo.visibleItemsInfo
                        val itemBeforeDraggingItem =
                            visibleItems.getOrNull(visibleItems.indexOfFirst { it.key == draggingItemKey } - 1)
                        var itemToAlmostScrollOff = itemBeforeDraggingItem ?: it
                        var scrollDistance =
                            itemToAlmostScrollOff.offset.toOffset().getAxis(orientation) +
                                    itemToAlmostScrollOff.size.getAxis(orientation) - 1f
                        if (scrollDistance <= 0f) {
                            itemToAlmostScrollOff = it
                            scrollDistance =
                                itemToAlmostScrollOff.offset.toOffset().getAxis(orientation) +
                                        itemToAlmostScrollOff.size.getAxis(orientation) - 1f
                        }

                        scrollDistance
                    }) ?: 0f
                },
                onScroll = {
                    moveDraggingItemToEnd(Scroller.Direction.FORWARD)
                }
            )
        } else {
            scroller.tryStop()
            false
        }

        if (!onMoveStateMutex.tryLock()) return
        if (!scroller.isScrolling && !isScrollingStarted) {
            val draggingItemRect = Rect(startOffset, endOffset)
            // find a target item to move with
            val targetItem = findTargetItem(
                draggingItemRect,
                items = state.layoutInfo.visibleItemsInfo,
            ) {
                it.index != draggingItem.index
            }
            if (targetItem != null) {
                scope.launch {
                    moveItems(draggingItem, targetItem)
                }
            }
        }
        onMoveStateMutex.unlock()
    }

    // keep dragging item in visible area to prevent it from disappearing
    private suspend fun moveDraggingItemToEnd(
        direction: Scroller.Direction,
    ) {
        // wait for the current moveItems to finish
        onMoveStateMutex.lock()

        val draggingItem = draggingItemLayoutInfo
        if (draggingItem == null) {
            onMoveStateMutex.unlock()
            return
        }
        val isDraggingItemAtEnd = when (direction) {
            Scroller.Direction.FORWARD -> draggingItem.index == state.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            Scroller.Direction.BACKWARD -> draggingItem.index == state.firstVisibleItemIndex
        }
        if (isDraggingItemAtEnd) {
            onMoveStateMutex.unlock()
            return
        }
        val dragOffset = draggingItemOffset.reverseAxisIfNecessary()
            .reverseAxisWithLayoutDirectionIfLazyVerticalStaggeredGridRtlFix()
        val startOffset = draggingItem.offset.toOffset() + dragOffset
        val endOffset = startOffset + draggingItem.size.toSize()
        val draggingItemRect = Rect(startOffset, endOffset).maxOutAxis(orientation)
        val targetItem = findTargetItem(
            draggingItemRect,
            items = state.layoutInfo.getItemsInContentArea(scrollThresholdPadding),
            direction.opposite,
        ) {
            // TODO(foundation v1.7.0): remove `state.firstVisibleItemIndex` check once foundation v1.7.0 is out
            it.index != state.firstVisibleItemIndex
        } ?: state.layoutInfo.getItemsInContentArea(
            scrollThresholdPadding
        ).let {
            val targetItemFunc = { item: LazyCollectionItemInfo<T> ->
                item.key in reorderableKeys && item.index != state.firstVisibleItemIndex
            }
            when (direction) {
                Scroller.Direction.FORWARD -> it.findLast(targetItemFunc)
                Scroller.Direction.BACKWARD -> it.find(targetItemFunc)
            }
        }
        val job = scope.launch {
            if (targetItem != null) {
                moveItems(draggingItem, targetItem)
            }
        }
        onMoveStateMutex.unlock()
        job.join()
    }

    private fun Rect.maxOutAxis(orientation: Orientation): Rect {
        return when (orientation) {
            Orientation.Vertical -> copy(
                top = Float.NEGATIVE_INFINITY,
                bottom = Float.POSITIVE_INFINITY,
            )

            Orientation.Horizontal -> copy(
                left = Float.NEGATIVE_INFINITY,
                right = Float.POSITIVE_INFINITY,
            )
        }
    }

    private fun findTargetItem(
        draggingItemRect: Rect,
        items: List<LazyCollectionItemInfo<T>> = state.layoutInfo.getItemsInContentArea(),
        direction: Scroller.Direction = Scroller.Direction.FORWARD,
        additionalPredicate: (LazyCollectionItemInfo<T>) -> Boolean = { true },
    ): LazyCollectionItemInfo<T>? {
        val targetItemFunc = { item: LazyCollectionItemInfo<T> ->
            val targetItemRect = Rect(item.offset.toOffset(), item.size.toSize())

            shouldItemMove(draggingItemRect, targetItemRect)
                    && item.key in reorderableKeys
                    && additionalPredicate(item)
        }
        val targetItem = when (direction) {
            Scroller.Direction.FORWARD -> items.find(targetItemFunc)
            Scroller.Direction.BACKWARD -> items.findLast(targetItemFunc)
        }
        return targetItem
    }

    private val layoutInfoFlow = snapshotFlow { state.layoutInfo }

    companion object {
        const val MoveItemsLayoutInfoUpdateMaxWaitDuration = 1000L
    }

    private suspend fun moveItems(
        draggingItem: LazyCollectionItemInfo<T>,
        targetItem: LazyCollectionItemInfo<T>,
    ) {
        if (draggingItem.index == targetItem.index) return

        val scrollToIndex = if (targetItem.index == state.firstVisibleItemIndex) {
            draggingItem.index
        } else if (draggingItem.index == state.firstVisibleItemIndex) {
            targetItem.index
        } else {
            null
        }
        if (scrollToIndex != null) {
            // this is needed to neutralize automatic keeping the first item first.
            // see https://github.com/Calvin-LL/Reorderable/issues/4
            state.scrollToItem(scrollToIndex, state.firstVisibleItemScrollOffset)
        }

        try {
            onMoveStateMutex.withLock {
                oldDraggingItemIndex = draggingItem.index

                scope.(onMoveState.value)(draggingItem.data, targetItem.data)

                predictedDraggingItemOffset = if (targetItem.index > draggingItem.index) {
                    (targetItem.offset + targetItem.size) - draggingItem.size
                } else {
                    targetItem.offset
                }

                withTimeout(MoveItemsLayoutInfoUpdateMaxWaitDuration) {
                    // the first result from layoutInfoFlow is the current layoutInfo
                    // the second result is the updated layoutInfo
                    layoutInfoFlow.take(2).collect()
                }

                predictedDraggingItemOffset = null
            }
        } catch (e: CancellationException) {
            // do nothing
        }
    }

    internal fun isItemDragging(key: Any): State<Boolean> {
        return derivedStateOf {
            key == draggingItemKey
        }
    }

    private fun getScrollSpeedMultiplier(distance: Float): Float {
        // map distance in scrollThreshold..-scrollThreshold to 1..10
        return (1 - ((distance + scrollThreshold) / (scrollThreshold * 2)).coerceIn(
            0f,
            1f
        )) * 10
    }
}