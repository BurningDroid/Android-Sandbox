package com.aaron.listre_order.ui.order

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

internal interface LazyCollectionLayoutInfo<out T> {
    val visibleItemsInfo: List<LazyCollectionItemInfo<T>>
    val viewportSize: IntSize
    val orientation: Orientation
    val reverseLayout: Boolean
    val beforeContentPadding: Int

    val mainAxisViewportSize: Int
        get() = when (orientation) {
            Orientation.Vertical -> viewportSize.height
            Orientation.Horizontal -> viewportSize.width
        }

    fun getScrollAreaOffsets(
        padding: AbsolutePixelPadding,
    ) = getScrollAreaOffsets(
        CollectionScrollPadding.fromAbsolutePixelPadding(
            orientation,
            padding,
            reverseLayout,
        )
    )

    fun getScrollAreaOffsets(padding: CollectionScrollPadding): ScrollAreaOffsets {
        val (startPadding, endPadding) = padding
        val contentEndOffset = when (orientation) {
            Orientation.Vertical -> viewportSize.height
            Orientation.Horizontal -> viewportSize.width
        } - endPadding

        return ScrollAreaOffsets(
            start = startPadding,
            end = contentEndOffset,
        )
    }

    /**
     * get items that are fully inside the content area
     */
    fun getItemsInContentArea(padding: AbsolutePixelPadding) = getItemsInContentArea(
        CollectionScrollPadding.fromAbsolutePixelPadding(
            orientation,
            padding,
            reverseLayout,
        )
    )

    /**
     * get items that are fully inside the content area
     */
    fun getItemsInContentArea(padding: CollectionScrollPadding = CollectionScrollPadding.Zero): List<LazyCollectionItemInfo<T>> {
        val (contentStartOffset, contentEndOffset) = getScrollAreaOffsets(
            padding
        )

        return when (orientation) {
            Orientation.Vertical -> {
                visibleItemsInfo.filter { item ->
                    item.offset.y >= contentStartOffset && item.offset.y + item.size.height <= contentEndOffset
                }
            }

            Orientation.Horizontal -> {
                visibleItemsInfo.filter { item ->
                    item.offset.x >= contentStartOffset && item.offset.x + item.size.width <= contentEndOffset
                }
            }
        }
    }
}

internal val LazyListLayoutInfo.mainAxisViewportSize: Int
    get() = when (orientation) {
        Orientation.Vertical -> viewportSize.height
        Orientation.Horizontal -> viewportSize.width
    }

internal fun LazyListItemInfo.toLazyCollectionItemInfo(orientation: Orientation) =
    object : LazyCollectionItemInfo<LazyListItemInfo> {
        override val index: Int
            get() = this@toLazyCollectionItemInfo.index
        override val key: Any
            get() = this@toLazyCollectionItemInfo.key
        override val offset: IntOffset
            get() = IntOffset.fromAxis(orientation, this@toLazyCollectionItemInfo.offset)
        override val size: IntSize
            get() = IntSize.fromAxis(orientation, this@toLazyCollectionItemInfo.size)
        override val data: LazyListItemInfo
            get() = this@toLazyCollectionItemInfo

    }

internal fun LazyListLayoutInfo.toLazyCollectionLayoutInfo() =
    object : LazyCollectionLayoutInfo<LazyListItemInfo> {
        override val visibleItemsInfo: List<LazyCollectionItemInfo<LazyListItemInfo>>
            get() = this@toLazyCollectionLayoutInfo.visibleItemsInfo.map {
                it.toLazyCollectionItemInfo(orientation)
            }
        override val viewportSize: IntSize
            get() = this@toLazyCollectionLayoutInfo.viewportSize
        override val orientation: Orientation
            get() = this@toLazyCollectionLayoutInfo.orientation
        override val reverseLayout: Boolean
            get() = this@toLazyCollectionLayoutInfo.reverseLayout
        override val beforeContentPadding: Int
            get() = this@toLazyCollectionLayoutInfo.beforeContentPadding

    }

internal fun LazyListState.toLazyCollectionState() =
    object : LazyCollectionState<LazyListItemInfo> {
        override val firstVisibleItemIndex: Int
            get() = this@toLazyCollectionState.firstVisibleItemIndex
        override val firstVisibleItemScrollOffset: Int
            get() = this@toLazyCollectionState.firstVisibleItemScrollOffset
        override val layoutInfo: LazyCollectionLayoutInfo<LazyListItemInfo>
            get() = this@toLazyCollectionState.layoutInfo.toLazyCollectionLayoutInfo()

        override suspend fun animateScrollBy(value: Float, animationSpec: AnimationSpec<Float>) =
            this@toLazyCollectionState.animateScrollBy(value, animationSpec)

        override suspend fun scrollToItem(scrollToIndex: Int, firstVisibleItemScrollOffset: Int) =
            this@toLazyCollectionState.scrollToItem(scrollToIndex, firstVisibleItemScrollOffset)
    }