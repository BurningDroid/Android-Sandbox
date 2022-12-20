package com.aaron.sample.dragndroplist.dragndrop

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.util.fastForEach
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.sign


class ReSortableState(
    val listState: LazyListState,
    private val scope: CoroutineScope,
    private val maxScrollPerFrame: Float,
    private val onMove: (fromIndex: ItemPosition, toIndex: ItemPosition) -> (Unit),
    private val canDragOver: ((draggedOver: ItemPosition, dragging: ItemPosition) -> Boolean)?,
    private val onDragEnd: ((startIndex: Int, endIndex: Int) -> (Unit))?,
    val dragCancelledAnimation: DragCancelledAnimation
) {
    var draggingItemIndex by mutableStateOf<Int?>(null)
        private set
    val draggingItemKey: Any?
        get() = selected?.itemKey
    private val LazyListItemInfo.left: Int
        get() = when {
            isVerticalScroll -> 0
            listState.layoutInfo.reverseLayout -> listState.layoutInfo.viewportSize.width - offset - size
            else -> offset
        }
    private val LazyListItemInfo.top: Int
        get() = when {
            !isVerticalScroll -> 0
            listState.layoutInfo.reverseLayout -> listState.layoutInfo.viewportSize.height - offset - size
            else -> offset
        }
    private val LazyListItemInfo.right: Int
        get() = when {
            isVerticalScroll -> 0
            listState.layoutInfo.reverseLayout -> listState.layoutInfo.viewportSize.width - offset
            else -> offset + size
        }
    private val LazyListItemInfo.bottom: Int
        get() = when {
            !isVerticalScroll -> 0
            listState.layoutInfo.reverseLayout -> listState.layoutInfo.viewportSize.height - offset
            else -> offset + size
        }
    private val LazyListItemInfo.width: Int
        get() = if (isVerticalScroll) 0 else size
    private val LazyListItemInfo.height: Int
        get() = if (isVerticalScroll) size else 0
    private val LazyListItemInfo.itemIndex: Int
        get() = index
    private val LazyListItemInfo.itemKey: Any
        get() = key
    private val visibleItemsInfo: List<LazyListItemInfo>
        get() = listState.layoutInfo.visibleItemsInfo
    private val firstVisibleItemIndex: Int
        get() = listState.firstVisibleItemIndex
    private val firstVisibleItemScrollOffset: Int
        get() = listState.firstVisibleItemScrollOffset
    private val viewportStartOffset: Int
        get() = listState.layoutInfo.viewportStartOffset
    private val viewportEndOffset: Int
        get() = listState.layoutInfo.viewportEndOffset
    internal val interactions = Channel<StartDrag>()
    internal val scrollChannel = Channel<Float>()
    val draggingItemLeft: Float
        get() = draggingLayoutInfo?.let { item ->
            (selected?.left ?: 0) + draggingDelta.x - item.left
        } ?: 0f
    val draggingItemTop: Float
        get() = draggingLayoutInfo?.let { item ->
            (selected?.top ?: 0) + draggingDelta.y - item.top
        } ?: 0f
    val isVerticalScroll: Boolean
        get() = listState.layoutInfo.orientation == Orientation.Vertical
    private val draggingLayoutInfo: LazyListItemInfo?
        get() = visibleItemsInfo
            .firstOrNull { it.itemIndex == draggingItemIndex }
    private var draggingDelta by mutableStateOf(Offset.Zero)
    private var selected by mutableStateOf<LazyListItemInfo?>(null)
    private var autoscroller: Job? = null
    private val targets = mutableListOf<LazyListItemInfo>()
    private val distances = mutableListOf<Int>()

    private suspend fun scrollToItem(index: Int, offset: Int) {
        listState.scrollToItem(index, offset)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    internal fun visibleItemsChanged() =
        snapshotFlow { draggingItemIndex != null }
            .flatMapLatest { if (it) snapshotFlow { visibleItemsInfo } else flowOf(null) }
            .filterNotNull()
            .distinctUntilChanged { old, new -> old.firstOrNull()?.itemIndex == new.firstOrNull()?.itemIndex && old.count() == new.count() }

    internal fun onDragStart(posX: Int, posY: Int): Boolean {
        val (x, y) = if (isVerticalScroll) {
            0 to posY + viewportStartOffset
        } else {
            posX + viewportStartOffset to 0
        }
        return visibleItemsInfo
            .find { x in it.left..it.right && y in it.top..it.bottom }
            ?.also {
                selected = it
                draggingItemIndex = it.itemIndex
            } != null
    }

    internal fun onDragCanceled() {
        val dragIdx = draggingItemIndex
        if (dragIdx != null) {
            val position = ItemPosition(dragIdx, selected?.itemKey)
            val offset = Offset(draggingItemLeft, draggingItemTop)
            scope.launch {
                dragCancelledAnimation.dragCancelled(position, offset)
            }
        }
        val startIndex = selected?.itemIndex
        val endIndex = draggingItemIndex
        selected = null
        draggingDelta = Offset.Zero
        draggingItemIndex = null
        cancelAutoScroll()
        onDragEnd?.apply {
            if (startIndex != null && endIndex != null) {
                invoke(startIndex, endIndex)
            }
        }
    }

    internal fun onDrag(offsetX: Int, offsetY: Int) {
        val selected = selected ?: return
        draggingDelta = Offset(draggingDelta.x + offsetX, draggingDelta.y + offsetY)
        val draggingItem = draggingLayoutInfo ?: return
        val startOffset = draggingItem.top + draggingItemTop
        val startOffsetX = draggingItem.left + draggingItemLeft
        chooseDropItem(
            draggingItem,
            findTargets(draggingDelta.x.toInt(), draggingDelta.y.toInt(), selected),
            startOffsetX.toInt(),
            startOffset.toInt()
        )?.also { targetItem ->
            if (targetItem.itemIndex == firstVisibleItemIndex || draggingItem.itemIndex == firstVisibleItemIndex) {
                scope.launch {
                    onMove.invoke(
                        ItemPosition(draggingItem.itemIndex, draggingItem.itemKey),
                        ItemPosition(targetItem.itemIndex, targetItem.itemKey)
                    )
                    scrollToItem(firstVisibleItemIndex, firstVisibleItemScrollOffset)
                }
            } else {
                onMove.invoke(
                    ItemPosition(draggingItem.itemIndex, draggingItem.itemKey),
                    ItemPosition(targetItem.itemIndex, targetItem.itemKey)
                )
            }
            draggingItemIndex = targetItem.itemIndex
        }

        with(calcAutoScrollOffset(0, maxScrollPerFrame)) {
            if (this != 0f) autoscroll(this)
        }
    }

    private fun autoscroll(scrollOffset: Float) {
        if (scrollOffset != 0f) {
            if (autoscroller?.isActive == true) {
                return
            }
            autoscroller = scope.launch {
                var scroll = scrollOffset
                var start = 0L
                while (scroll != 0f && autoscroller?.isActive == true) {
                    withFrameMillis {
                        if (start == 0L) {
                            start = it
                        } else {
                            scroll = calcAutoScrollOffset(it - start, maxScrollPerFrame)
                        }
                    }
                    scrollChannel.trySend(scroll)
                }
            }
        } else {
            cancelAutoScroll()
        }
    }

    private fun cancelAutoScroll() {
        autoscroller?.cancel()
        autoscroller = null
    }

    private fun findTargets(posX: Int, posY: Int, selected: LazyListItemInfo): List<LazyListItemInfo> {
        val (x, y) = if (isVerticalScroll) {
            0 to posY
        } else {
            posX to 0
        }

        targets.clear()
        distances.clear()
        val left = x + selected.left
        val right = x + selected.right
        val top = y + selected.top
        val bottom = y + selected.bottom
        val centerX = (left + right) / 2
        val centerY = (top + bottom) / 2
        visibleItemsInfo.fastForEach { item ->
            if (
                item.itemIndex == draggingItemIndex
                || item.bottom < top
                || item.top > bottom
                || item.right < left
                || item.left > right
            ) {
                return@fastForEach
            }
            if (canDragOver?.invoke(
                    ItemPosition(item.itemIndex, item.itemKey),
                    ItemPosition(selected.itemIndex, selected.itemKey)
                ) != false
            ) {
                val dx = (centerX - (item.left + item.right) / 2).absoluteValue
                val dy = (centerY - (item.top + item.bottom) / 2).absoluteValue
                val dist = dx * dx + dy * dy
                var pos = 0
                for (j in targets.indices) {
                    if (dist > distances[j]) {
                        pos++
                    } else {
                        break
                    }
                }
                targets.add(pos, item)
                distances.add(pos, dist)
            }
        }
        return targets
    }

    private fun chooseDropItem(
        draggedItemInfo: LazyListItemInfo?,
        items: List<LazyListItemInfo>,
        offsetX: Int,
        offsetY: Int
    ): LazyListItemInfo? {
        val (curX, curY) = if (isVerticalScroll) {
            0 to offsetY
        } else {
            offsetX to 0
        }

        if (draggedItemInfo == null) {
            return if (draggingItemIndex != null) items.lastOrNull() else null
        }
        var target: LazyListItemInfo? = null
        var highScore = -1
        val right = curX + draggedItemInfo.width
        val bottom = curY + draggedItemInfo.height
        val dx = curX - draggedItemInfo.left
        val dy = curY - draggedItemInfo.top

        items.fastForEach { item ->
            if (dx > 0) {
                val diff = item.right - right
                if (diff < 0 && item.right > draggedItemInfo.right) {
                    val score = diff.absoluteValue
                    if (score > highScore) {
                        highScore = score
                        target = item
                    }
                }
            }
            if (dx < 0) {
                val diff = item.left - curX
                if (diff > 0 && item.left < draggedItemInfo.left) {
                    val score = diff.absoluteValue
                    if (score > highScore) {
                        highScore = score
                        target = item
                    }
                }
            }
            if (dy < 0) {
                val diff = item.top - curY
                if (diff > 0 && item.top < draggedItemInfo.top) {
                    val score = diff.absoluteValue
                    if (score > highScore) {
                        highScore = score
                        target = item
                    }
                }
            }
            if (dy > 0) {
                val diff = item.bottom - bottom
                if (diff < 0 && item.bottom > draggedItemInfo.bottom) {
                    val score = diff.absoluteValue
                    if (score > highScore) {
                        highScore = score
                        target = item
                    }
                }
            }
        }
        return target
    }

    private fun calcAutoScrollOffset(time: Long, maxScroll: Float): Float {
        val draggingItem = draggingLayoutInfo ?: return 0f
        val startOffset: Float
        val endOffset: Float
        val delta: Float
        if (isVerticalScroll) {
            startOffset = draggingItem.top + draggingItemTop
            endOffset = startOffset + draggingItem.height
            delta = draggingDelta.y
        } else {
            startOffset = draggingItem.left + draggingItemLeft
            endOffset = startOffset + draggingItem.width
            delta = draggingDelta.x
        }
        return when {
            delta > 0 ->
                (endOffset - viewportEndOffset).coerceAtLeast(0f)
            delta < 0 ->
                (startOffset - viewportStartOffset).coerceAtMost(0f)
            else -> 0f
        }
            .let { interpolateOutOfBoundsScroll((endOffset - startOffset).toInt(), it, time, maxScroll) }
    }


    companion object {
        private const val ACCELERATION_LIMIT_TIME_MS: Long = 1500
        private val EaseOutQuadInterpolator: (Float) -> (Float) = {
            val t = 1 - it
            1 - t * t * t * t
        }
        private val EaseInQuintInterpolator: (Float) -> (Float) = {
            it * it * it * it * it
        }

        private fun interpolateOutOfBoundsScroll(
            viewSize: Int,
            viewSizeOutOfBounds: Float,
            time: Long,
            maxScroll: Float,
        ): Float {
            if (viewSizeOutOfBounds == 0f) return 0f
            val outOfBoundsRatio = min(1f, 1f * viewSizeOutOfBounds.absoluteValue / viewSize)
            val cappedScroll = sign(viewSizeOutOfBounds) * maxScroll * EaseOutQuadInterpolator(outOfBoundsRatio)
            val timeRatio = if (time > ACCELERATION_LIMIT_TIME_MS) 1f else time.toFloat() / ACCELERATION_LIMIT_TIME_MS
            return (cappedScroll * EaseInQuintInterpolator(timeRatio)).let {
                if (it == 0f) {
                    if (viewSizeOutOfBounds > 0) 1f else -1f
                } else {
                    it
                }
            }
        }
    }
}
