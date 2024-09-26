package com.aaron.kointest.ui.nav

sealed interface NavPage {

    val route: String

    data object Home : NavPage {
        override val route: String = "home"
    }

    data object Dashboard: NavPage {
        override val route: String = "dashboard"
    }
}