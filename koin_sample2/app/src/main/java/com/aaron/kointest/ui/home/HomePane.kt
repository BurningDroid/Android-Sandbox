package com.aaron.kointest.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.aaron.kointest.ui.common.ObserveLifecycle
import com.aaron.kointest.ui.nav.NavPage
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomePane(
    vm: HomeViewModel = koinViewModel(),
    navController: NavHostController
) {
    ObserveLifecycle {
        vm.loadData()
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Greeting(
                name = vm.greeting,
                modifier = Modifier.padding(innerPadding)
            )

            Button(onClick = {
                navController.navigate(NavPage.Dashboard.route)
            }) {
                Text("Go")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}