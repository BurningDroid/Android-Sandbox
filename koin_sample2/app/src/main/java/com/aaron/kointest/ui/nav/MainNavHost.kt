package com.aaron.kointest.ui.nav

import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aaron.kointest.ui.dashboard.DashboardPane
import com.aaron.kointest.ui.home.HomePane

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavPage.Home.route,
        modifier = Modifier.imePadding()
    ) {
        composable(
            route = NavPage.Home.route,
        ) {
            HomePane(
                navController = navController
            )
        }

        composable(
            route = NavPage.Dashboard.route
        ) {
            DashboardPane()
        }
    }
}
