package com.aaron.sample.chapter46

sealed class NavRoutes(
    val route: String
) {
    object Home : NavRoutes("home")
    object Welcome : NavRoutes("welcome")
    object Profile : NavRoutes("profile")
}