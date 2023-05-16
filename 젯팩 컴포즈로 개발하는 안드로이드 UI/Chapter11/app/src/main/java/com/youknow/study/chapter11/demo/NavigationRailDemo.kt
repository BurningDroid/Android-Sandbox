package com.youknow.study.chapter11.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.youknow.study.chapter11.R


@Composable
fun NavigationRailDemo() {
    val showNavigationRail = LocalConfiguration.current.screenWidthDp >= 600
    val index = remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            })
        },
        bottomBar = {
            if (!showNavigationRail)
                BottomBar(index)
        }
    ) { padding ->
        Content(
            modifier = Modifier.padding(padding),
            showNavigationRail = showNavigationRail,
            index = index
        )
    }
}

@Composable
fun BottomBar(index: MutableState<Int>) {
    BottomNavigation {
        for (i in 0..2)
            BottomNavigationItem(
                selected = i == index.value,
                onClick = { index.value = i },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_android),
                        contentDescription = null
                    )
                },
                label = {
                    MyText(index = i)
                }
            )
    }
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    showNavigationRail: Boolean,
    index: MutableState<Int>
) {
    Row(
        modifier = modifier.fillMaxSize()
    ) {
        if (showNavigationRail) {
            NavigationRail {
                for (i in 0..2)
                    NavigationRailItem(
                        selected = i == index.value,
                        onClick = {
                            index.value = i
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_android),
                                contentDescription = null
                            )
                        },
                        label = {
                            MyText(index = i)
                        }
                    )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.surface),
            contentAlignment = Alignment.Center
        ) {
            MyText(
                index = index.value,
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Composable
fun MyText(index: Int, style: TextStyle = LocalTextStyle.current) {
    Text(
        text = "#${index + 1}",
        style = style
    )
}