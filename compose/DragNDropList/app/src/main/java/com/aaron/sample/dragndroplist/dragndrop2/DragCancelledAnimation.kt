package com.aaron.sample.dragndroplist.dragndrop2

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

class DragCancelledAnimation(
    private val stiffness: Float = Spring.StiffnessMediumLow
) {

    private val animatable = Animatable(Offset.Zero, Offset.VectorConverter)

    val offset: Offset
        get() = animatable.value

    var position by mutableStateOf<ItemPosition?>(null)
        private set

    suspend fun dragCancelled(position: ItemPosition, offset: Offset) {
        this.position = position
        animatable.snapTo(offset)
        animatable.animateTo(
            Offset.Zero,
            spring(stiffness = stiffness, visibilityThreshold = Offset.VisibilityThreshold)
        )
        this.position = null
    }
}