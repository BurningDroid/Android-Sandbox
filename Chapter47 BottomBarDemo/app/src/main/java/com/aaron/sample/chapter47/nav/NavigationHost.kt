package com.aaron.sample.chapter47.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aaron.sample.chapter47.ui.screen.Contacts
import com.aaron.sample.chapter47.ui.screen.Favorites
import com.aaron.sample.chapter47.ui.screen.Home

@Composable
fun NavigationHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route) {
            Home()
        }

        composable(NavRoutes.Contacts.route) {
            Contacts()
        }

        composable(NavRoutes.Favorites.route) {
            Favorites()
        }
    }
}