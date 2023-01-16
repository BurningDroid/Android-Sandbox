package com.aaron.sample.chapter47.nav

sealed class NavRoutes(val route: String) {
    object Home: NavRoutes("home")
    object Contacts: NavRoutes("contacts")
    object Favorites: NavRoutes("favorites")
}