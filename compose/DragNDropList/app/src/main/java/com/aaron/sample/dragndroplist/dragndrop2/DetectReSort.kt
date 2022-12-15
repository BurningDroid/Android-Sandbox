package com.aaron.sample.dragndroplist.dragndrop2

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput


fun Modifier.detectReSortAfterLongPress(state: ReSortableState) =
    this.then(
        Modifier.pointerInput(Unit) {
            forEachGesture {
                val down = awaitPointerEventScope {
                    awaitFirstDown(requireUnconsumed = false)
                }
                awaitLongPressOrCancellation(down)?.also {
                    state.interactions.trySend(StartDrag(down.id))
                }
            }
        }
    )