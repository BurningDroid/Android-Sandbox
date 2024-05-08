package com.aaron.annotationtest.ui.main.model

import androidx.compose.runtime.Immutable

@Immutable
data class StableData(
    val progress: Int = 0
)