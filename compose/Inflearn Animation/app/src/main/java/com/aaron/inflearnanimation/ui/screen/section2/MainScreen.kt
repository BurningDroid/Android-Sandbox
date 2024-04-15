package com.aaron.inflearnanimation.ui.screen.section2

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaron.inflearnanimation.R
import com.aaron.inflearnanimation.ui.animation.TextAnimation
import com.aaron.inflearnanimation.ui.theme.InflearnAnimationTheme
import kotlinx.coroutines.delay

@Composable
fun MainScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Black)
    ) {
        item { Header() }
        item { TopMenu() }
        item { TopMenuBottom() }
        item { SpendThisMonth() }
        item { SpendThisMonthProgressBar() }
        item { SpendThisMonthCategoryList() }
        item { SpaceGray() }
        item { SpendGraphHeader() }
        item { SpendGraph() }
        item { SpaceGray() }
        item { SpendThisMonthInsuranceHeader() }
        item { SpendThisMonthInsuranceGraph() }
    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//            .background(Color.Black)
//    ) {
//        Header()
//        TopMenu()
//        TopMenuBottom()
//        SpendThisMonth()
//        SpendThisMonthProgressBar()
//        SpendThisMonthCategoryList()
//        SpaceGray()
//        SpendGraphHeader()
//        SpendGraph()
//        SpaceGray()
//        SpendThisMonthInsuranceHeader()
//        SpendThisMonthInsuranceGraph()
//    }
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

    TextAnimation(
        targetValue = 1000000,
        textColor = White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        padding = PaddingValues(start = 20.dp)
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
    var startAnimation by remember { mutableStateOf(false) }

    val weightList = listOf(7f, 1f, 1f, 1f)
    val animatedWeights = weightList.map {
        animateFloatAsState(
            targetValue = if (startAnimation) it else 0.1F,
            animationSpec = tween(durationMillis = 3_000)
        )
    }

    LaunchedEffect(key1 = Unit) {
        startAnimation = true
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(end = 5.dp)
                .weight(animatedWeights[0].value)
                .height(30.dp)
                .background(Red, RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp))
        )
        Box(
            modifier = Modifier
                .padding(end = 5.dp)
                .weight(animatedWeights[1].value)
                .height(30.dp)
                .background(Gray)
        )
        Box(
            modifier = Modifier
                .padding(end = 5.dp)
                .weight(animatedWeights[2].value)
                .height(30.dp)
                .background(Blue)
        )
        Box(
            modifier = Modifier
                .weight(animatedWeights[3].value)
                .height(30.dp)
                .background(Green, RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp))
        )
    }
}

@Composable
fun SpendThisMonthCategoryList() {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Red, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_category1),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = White
                    )
                }

                Column {
                    Text(text = "이체", color = White)
                    Text(text = "70%", color = White)
                }
            }

            Text(
                text = "700,000원",
                color = White,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Gray, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_category2),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = White
                    )
                }

                Column {
                    Text(text = "송금", color = White)
                    Text(text = "10%", color = White)
                }
            }

            Text(
                text = "100,000원",
                color = White,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Blue, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_category3),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = White
                    )
                }

                Column {
                    Text(text = "저금", color = White)
                    Text(text = "10%", color = White)
                }
            }

            Text(
                text = "100,000원",
                color = White,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Green, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_category4),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = White
                    )
                }

                Column {
                    Text(text = "월세", color = White)
                    Text(text = "10%", color = White)
                }
            }

            Text(
                text = "100,000원",
                color = White,
            )
        }
    }
}

@Composable
fun SpaceGray() {
    Box(
        modifier = Modifier
            .padding(vertical = 40.dp)
            .fillMaxWidth()
            .height(10.dp)
            .background(
                DarkGray
            )
    )
}

@Composable
fun SpendGraphHeader() {
    Text(
        text = "이번달",
        color = White,
        modifier = Modifier.padding(start = 15.dp)
    )
}

@Composable
fun SpendGraph() {
    val dotPosition = listOf(400F, 300F, 750F, 600F, 800F)
    val dotPositionNew = listOf(500F, 200F, 700F)

    val showHideEffect = rememberInfiniteTransition()
    val showHideAnimation = showHideEffect.animateFloat(
        initialValue = 0F,
        targetValue = 1F,
        animationSpec = infiniteRepeatable(
            animation = tween(1_000)
        )
    )

    Canvas(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val width = size.width
        val height = size.height

        val maxPosition = dotPosition.max()
        val minPosition = dotPosition.min()

        val positionMap = dotPosition.mapIndexed { idx, value ->
            val x = (width / (dotPosition.size - 1)) * idx
            val y = height - (height * (value - minPosition) / (maxPosition - minPosition))
            x to y
        }

        positionMap.zipWithNext { a, b ->
            drawLine(
                color = Gray,
                start = Offset(a.first, a.second),
                end = Offset(b.first, b.second),
                strokeWidth = 5F,
                cap = Stroke.DefaultCap
            )
        }

        val positionMapNew = dotPositionNew.mapIndexed { idx, value ->
            val x = (width / (dotPosition.size - 1)) * idx
            val y = height - (height * (value - minPosition) / (maxPosition - minPosition))
            x to y
        }

        positionMapNew.zipWithNext { a, b ->
            drawLine(
                color = Blue,
                start = Offset(a.first, a.second),
                end = Offset(b.first, b.second),
                strokeWidth = 5F,
                cap = Stroke.DefaultCap
            )
        }

        positionMapNew.lastOrNull()?.let {
            drawCircle(
                color = Blue,
                radius = 13F,
                center = Offset(it.first, it.second),
                alpha = showHideAnimation.value
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun SpendThisMonthInsuranceHeader() {
    Text(
        text = "매달 내는 보험료 적절할까요?",
        color = White,
        modifier = Modifier.padding(start = 15.dp)
    )
}

@Composable
fun SpendThisMonthInsuranceGraph() {

    var leftBoxHeight by remember { mutableStateOf(160.dp) }
    val animatedLeftBoxHeight = animateDpAsState(
        targetValue = leftBoxHeight,
        animationSpec = tween(durationMillis = 4_000)
    )

    var rightBoxHeight by remember { mutableStateOf(40.dp) }
    val animatedRightBoxHeight = animateDpAsState(
        targetValue = rightBoxHeight,
        animationSpec = tween(durationMillis = 4_000)
    )

    LaunchedEffect(key1 = Unit) {
        while (true) {
            leftBoxHeight = 40.dp
            rightBoxHeight = 160.dp

            delay(4000)

            leftBoxHeight = 160.dp
            rightBoxHeight = 40.dp

            delay(4000)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(top = 30.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .width(128.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "과일", color = White)

                Box(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .width(60.dp)
                        .height(animatedLeftBoxHeight.value)
                        .background(Red, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                )

                Text(
                    text = "600,000원",
                    color = White,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .width(128.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "부족", color = White)

                Box(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .width(60.dp)
                        .height(animatedRightBoxHeight.value)
                        .background(Blue, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                )

                Text(
                    text = "???,???원",
                    color = White,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.padding(20.dp))
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    InflearnAnimationTheme {
        MainScreen()
    }
}