package com.aaron.flippingcard.ui.page.model

import androidx.annotation.DrawableRes
import com.aaron.flippingcard.R

data class CardItem(
    @DrawableRes
    val image: Int = R.drawable.img_jedi,
    val message: String = "",
    val isFlipped: Boolean = false,
)