package com.aaron.flippingcard.ui.page

data class Accelerometer(
    val x: Float = 0F,
    val y: Float = 0F,
    val z: Float = 0F,
) {
    override fun toString(): String {
        return "x: $x\ny: $y\nz: $z"
    }
}