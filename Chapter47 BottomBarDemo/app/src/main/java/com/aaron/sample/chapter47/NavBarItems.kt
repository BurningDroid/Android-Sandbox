package com.aaron.sample.chapter47

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home

object NavBarItems {
    val barItems = listOf(
        BarItem("Home", Icons.Filled.Home, "home"),
        BarItem("Contacts", Icons.Filled.Face, "contacts"),
        BarItem("Favorites", Icons.Filled.Favorite, "favorites"),
    )
}