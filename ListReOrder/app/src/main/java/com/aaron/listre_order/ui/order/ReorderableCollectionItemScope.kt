package com.aaron.listre_order.ui.order

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.launch

@Stable
class ReorderableCollectionItemScope(
    private val reorderableLazyCollectionState: ReorderableLazyCollectionState<*>,
    private val key: Any,
    private val itemPositionProvider: () -> Offset,
) {

    fun Modifier.draggableHandle(
        enabled: Boolean = true,
        interactionSource: MutableInteractionSource? = null,
        onDragStarted: (startedPosition: Offset) -> Unit = {},
        onDragStopped: () -> Unit = {},
    ) = composed {
        var handleOffset by remember { mutableStateOf(Offset.Zero) }
        var handleSize by remember { mutableStateOf(IntSize.Zero) }

        val coroutineScope = rememberCoroutineScope()

        onGloballyPositioned {
            handleOffset = it.positionInRoot()
            handleSize = it.size
        }.draggable(
            key1 = reorderableLazyCollectionState,
            enabled = enabled && (reorderableLazyCollectionState.isItemDragging(key).value || !reorderableLazyCollectionState.isAnyItemDragging),
            interactionSource = interactionSource,
            onDragStarted = {
                coroutineScope.launch {
                    val handleOffsetRelativeToItem = handleOffset - itemPositionProvider()
                    val handleCenter = Offset(
                        handleOffsetRelativeToItem.x + handleSize.width / 2f,
                        handleOffsetRelativeToItem.y + handleSize.height / 2f
                    )

                    reorderableLazyCollectionState.onDragStart(key, handleCenter)
                }
                onDragStarted(it)
            },
            onDragStopped = {
                reorderableLazyCollectionState.onDragStop()
                onDragStopped()
            },
            onDrag = { change, dragAmount ->
                change.consume()
                reorderableLazyCollectionState.onDrag(dragAmount)
            },
        )
    }
}