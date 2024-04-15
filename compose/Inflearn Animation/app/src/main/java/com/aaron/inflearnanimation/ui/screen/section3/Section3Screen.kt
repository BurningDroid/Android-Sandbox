package com.aaron.inflearnanimation.ui.screen.section3

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaron.inflearnanimation.R
import java.util.Calendar

@Composable
fun Section3Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CalendarHeader()

        CalendarLazyList()
    }
}

@Composable
fun CalendarHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_category1),
            contentDescription = null,
            modifier = Modifier.padding(8.dp)
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_category2),
            contentDescription = null,
            modifier = Modifier.padding(8.dp)
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_category3),
            contentDescription = null,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun CalendarDayNames() {
    val nameList = listOf("일", "월", "화", "수", "목", "금", "토")
    Row {
        nameList.forEach {
            Box(
                modifier = Modifier.weight(1F),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun CalendarDayList() {
    val time = remember { mutableStateOf(Calendar.getInstance()) }
    val date = time.value
    date.set(Calendar.YEAR, 2023)
    date.set(Calendar.MONDAY, Calendar.DECEMBER)
    date.set(Calendar.DAY_OF_MONTH, 1)

    val thisMonthDayMax = date.getActualMaximum(Calendar.DAY_OF_MONTH)
    val thisMonthFirstDay = date.get(Calendar.DAY_OF_WEEK) - 1
    val thisMonthWeeksCount = (thisMonthDayMax + thisMonthFirstDay + 6) / 7

    Column(
        modifier = Modifier.padding(top = 20.dp)
    ) {
        repeat(thisMonthWeeksCount) { week ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(7) { day ->
                    val resultDay = week * 7 + day - thisMonthFirstDay + 1
                    if (resultDay in 1..thisMonthDayMax) {
                        Box(
                            modifier = Modifier
                                .weight(1F)
                                .height(60.dp)
                                .border(1.dp, Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(20.dp)
                                        .background(Color(0xFF89CFF0))
                                ) {
                                    val income = CalendarData.spendingData[resultDay]?.get(0)?.price ?: 0
                                    val outcome = CalendarData.spendingData[resultDay]?.get(1)?.price ?: 0

                                    val result = income - outcome
                                    if (result < 0) {
                                        Text(text = result.toString(), color = Blue)
                                    } else {
                                        Text(text = result.toString(), color = Red)
                                    }
                                }

                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = resultDay.toString(),
                                        color = Color.Blue
                                    )
                                }
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .weight(1F)
                                .height(60.dp)
                                .border(1.dp, Color.Gray)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(20.dp)
                                        .background(Color(0xFF89CFF0))
                                ) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarLazyList() {
    val lazyColumnState = rememberLazyListState()
    val lazyRowState = rememberLazyListState()
    val isScrolling = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { lazyColumnState.isScrollInProgress }.collect {
            isScrolling.value = it
        }
    }

    LaunchedEffect(key1 = lazyColumnState.firstVisibleItemIndex) {
        lazyRowState.scrollToItem(lazyColumnState.firstVisibleItemIndex)
    }

    if (isScrolling.value) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            state = lazyRowState
        ) {
            items(CalendarData.spendingData.size) {
                val day = it + 1
                Box(
                    modifier = Modifier
                        .width(55.dp)
                        .height(60.dp)
                        .border(1.dp, Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                                .background(Color(0xFF89CFF0))
                        ) {
                            val income = CalendarData.spendingData[day]?.get(0)?.price ?: 0
                            val outcome = CalendarData.spendingData[day]?.get(1)?.price ?: 0

                            val result = income - outcome
                            if (result < 0) {
                                Text(text = result.toString(), color = Blue)
                            } else {
                                Text(text = result.toString(), color = Red)
                            }
                        }

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.toString(),
                                color = Color.Blue
                            )
                        }
                    }
                }
            }
        }
    } else {
        CalendarDayNames()
        CalendarDayList()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        state = lazyColumnState
    ) {
        CalendarData.spendingData.keys.forEach { day ->
            item {
                Text(
                    text = "2023. 12. $day",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                CalendarData.spendingData[day]?.forEach { data ->
                    Row(
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Text(text = data.type)
                        Text(
                            text = "${data.price}",
                            color = if (data.type == "수입") Red else Blue
                        )
                    }
                }
            }
        }
    }
}