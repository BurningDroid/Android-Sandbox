package com.aaron.listre_order.ui.order

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.zIndex
import kotlinx.coroutines.CoroutineScope

@Stable
class ReorderableLazyListState internal constructor(
    state: LazyListState,
    scope: CoroutineScope,
    onMoveState: State<suspend CoroutineScope.(from: LazyListItemInfo, to: LazyListItemInfo) -> Unit>,
    scrollThreshold: Float,
    scrollThresholdPadding: AbsolutePixelPadding,
    scroller: Scroller,
    layoutDirection: LayoutDirection,
    shouldItemMove: (draggingItem: Rect, item: Rect) -> Boolean,
) : ReorderableLazyCollectionState<LazyListItemInfo>(
    state.toLazyCollectionState(),
    scope,
    onMoveState,
    scrollThreshold,
    scrollThresholdPadding,
    scroller,
    layoutDirection,
    shouldItemMove = shouldItemMove,
)

@ExperimentalFoundationApi
@Composable
fun LazyItemScope.ReorderableItem(
    state: ReorderableLazyListState,
    key: Any,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    animateItemModifier: Modifier = Modifier.animateItemPlacement(),
    content: @Composable ReorderableCollectionItemScope.(isDragging: Boolean) -> Unit,
) {
    val orientation by derivedStateOf { state.orientation }
    val dragging by state.isItemDragging(key)
    val offsetModifier = if (dragging) {
        Modifier
            .zIndex(1f)
            .then(when (orientation) {
                Orientation.Vertical -> Modifier.graphicsLayer {
                    translationY = state.draggingItemOffset.y
                }

                Orientation.Horizontal -> Modifier.graphicsLayer {
                    translationX = state.draggingItemOffset.x
                }
            })
    } else if (key == state.previousDraggingItemKey) {
        Modifier
            .zIndex(1f)
            .then(when (orientation) {
                Orientation.Vertical -> Modifier.graphicsLayer {
                    translationY = state.previousDraggingItemOffset.value.y
                }

                Orientation.Horizontal -> Modifier.graphicsLayer {
                    translationX = state.previousDraggingItemOffset.value.x
                }
            })
    } else {
        animateItemModifier
    }

    ReorderableCollectionItem(
        state = state,
        key = key,
        modifier = modifier.then(offsetModifier),
        enabled = enabled,
        dragging = dragging,
        content = content,
    )
}

@ExperimentalFoundationApi
@Composable
internal fun ReorderableCollectionItem(
    state: ReorderableLazyCollectionState<*>,
    key: Any,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    dragging: Boolean,
    content: @Composable ReorderableCollectionItemScope.(isDragging: Boolean) -> Unit,
) {
    var itemPosition by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier.onGloballyPositioned {
            itemPosition = it.positionInRoot()
        }
    ) {
        val itemScope = remember(state, key) {
            ReorderableCollectionItemScope(
                reorderableLazyCollectionState = state,
                key = key,
                itemPositionProvider = { itemPosition },
            )
        }
        itemScope.content(dragging)
    }

    LaunchedEffect(state.reorderableKeys, enabled) {
        if (enabled) {
            state.reorderableKeys.add(key)
        } else {
            state.reorderableKeys.remove(key)
        }
    }
}