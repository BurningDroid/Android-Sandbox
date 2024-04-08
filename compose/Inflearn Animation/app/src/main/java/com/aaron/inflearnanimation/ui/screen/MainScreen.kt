package com.aaron.inflearnanimation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaron.inflearnanimation.ui.theme.InflearnAnimationTheme

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.Black)
    ) {
        Header()
        TopMenu()
        TopMenuBottom()
        SpendThisMonth()
        SpendThisMonthProgressBar()
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
        Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = Color.White)
    }
}

@Composable
fun TopMenu() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
    ) {
        Box(
            modifier = Modifier.weight(1F),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "자산",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
        Box(
            modifier = Modifier.weight(1F),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "수입/지출",
                color = Color.White,
                fontSize = 16.sp
            )
        }
        Box(
            modifier = Modifier.weight(1F),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "연말정산",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun TopMenuBottom() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
    ) {
        Box(
            modifier = Modifier.weight(1F)
        )
        Box(
            modifier = Modifier
                .weight(1F)
                .padding(top = 15.dp, start = 5.dp, end = 5.dp)
                .height(2.dp)
                .background(Color.White)
        )
        Box(
            modifier = Modifier.weight(1F),
        )
    }
}

@Composable
fun SpendThisMonth() {
    Row(
        modifier = Modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null, tint = White)
        Text(
            text = "11월 소비",
            color = White,
            modifier = Modifier.padding(horizontal = 5.dp),
            textDecoration = TextDecoration.Underline
        )
        Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null, tint = White)
    }

    Text(
        text = "1,000,000원",
        modifier = Modifier.padding(start = 20.dp),
        color = White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    Text(
        text = "계정에서 쓴 금액 포함",
        modifier = Modifier.padding(start = 20.dp, top = 10.dp),
        color = Gray,
        fontSize = 14.sp
    )
}

@Composable
fun SpendThisMonthProgressBar() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Box(
            modifier = Modifier.padding(end = 5.dp).weight(7F).height(30.dp).background(Red, RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp))
        )
        Box(
            modifier = Modifier.padding(end = 5.dp).weight(1F).height(30.dp).background(Gray)
        )
        Box(
            modifier = Modifier.padding(end = 5.dp).weight(1F).height(30.dp).background(Blue)
        )
        Box(
            modifier = Modifier.weight(1F).height(30.dp).background(Green, RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    InflearnAnimationTheme {
        MainScreen()
    }
}