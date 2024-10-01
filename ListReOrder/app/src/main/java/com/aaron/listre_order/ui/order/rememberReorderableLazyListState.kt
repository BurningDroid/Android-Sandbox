package com.aaron.listre_order.ui.order

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberReorderableLazyListState(
    lazyListState: LazyListState,
    scrollThresholdPadding: PaddingValues = PaddingValues(0.dp),
    scrollThreshold: Dp = 48.dp,
    scroller: Scroller = rememberScroller(
        scrollableState = lazyListState,
        pixelAmountProvider = { lazyListState.layoutInfo.mainAxisViewportSize * ScrollAmountMultiplier },
    ),
    onMove: suspend CoroutineScope.(from: LazyListItemInfo, to: LazyListItemInfo) -> Unit,
): ReorderableLazyListState {
    val density = LocalDensity.current
    val scrollThresholdPx = with(density) { scrollThreshold.toPx() }

    val scope = rememberCoroutineScope()
    val onMoveState = rememberUpdatedState(onMove)
    val layoutDirection = LocalLayoutDirection.current
    val absoluteScrollThresholdPadding = AbsolutePixelPadding(
        start = with(density) {
            scrollThresholdPadding.calculateStartPadding(layoutDirection).toPx()
        },
        end = with(density) {
            scrollThresholdPadding.calculateEndPadding(layoutDirection).toPx()
        },
        top = with(density) { scrollThresholdPadding.calculateTopPadding().toPx() },
        bottom = with(density) { scrollThresholdPadding.calculateBottomPadding().toPx() },
    )
    val orientation by derivedStateOf { lazyListState.layoutInfo.orientation }
    val state = remember(
        scope,
        lazyListState,
        scrollThreshold,
        scrollThresholdPadding,
        scroller,
        orientation,
    ) {
        ReorderableLazyListState(
            state = lazyListState,
            scope = scope,
            onMoveState = onMoveState,
            scrollThreshold = scrollThresholdPx,
            scrollThresholdPadding = absoluteScrollThresholdPadding,
            scroller = scroller,
            layoutDirection = layoutDirection,
            shouldItemMove = when (orientation) {
                Orientation.Vertical -> { draggingItem, item ->
                    item.center.y in draggingItem.top..<draggingItem.bottom
                }

                Orientation.Horizontal -> { draggingItem, item ->
                    item.center.x in draggingItem.left..<draggingItem.right
                }
            },
        )
    }
    return state
}

internal const val ScrollAmountMultiplier = 0.05f

internal data class AbsolutePixelPadding(
    val start: Float,
    val end: Float,
    val top: Float,
    val bottom: Float,
)

internal interface LazyCollectionItemInfo<out T> {
    val index: Int
    val key: Any
    val offset: IntOffset
    val size: IntSize
    val data: T

    val center: IntOffset
        get() = IntOffset(offset.x + size.width / 2, offset.y + size.height / 2)
}

internal data class CollectionScrollPadding(
    val start: Float,
    val end: Float,
) {
    companion object {
        val Zero = CollectionScrollPadding(0f, 0f)

        fun fromAbsolutePixelPadding(
            orientation: Orientation,
            padding: AbsolutePixelPadding,
            reverseLayout: Boolean,
        ): CollectionScrollPadding {
            return when (orientation) {
                Orientation.Vertical -> CollectionScrollPadding(
                    start = padding.top,
                    end = padding.bottom,
                )

                Orientation.Horizontal -> CollectionScrollPadding(
                    start = padding.start,
                    end = padding.end,
                )
            }.let {
                when (reverseLayout) {
                    true -> CollectionScrollPadding(
                        start = it.end,
                        end = it.start,
                    )

                    false -> it
                }
            }
        }
    }
}

internal data class ScrollAreaOffsets(
    val start: Float,
    val end: Float,
)

internal interface LazyCollectionState<out T> {
    val firstVisibleItemIndex: Int
    val firstVisibleItemScrollOffset: Int
    val layoutInfo: LazyCollectionLayoutInfo<T>

    suspend fun animateScrollBy(
        value: Float,
        animationSpec: AnimationSpec<Float> = spring(),
    ): Float

    suspend fun scrollToItem(scrollToIndex: Int, firstVisibleItemScrollOffset: Int)
}
