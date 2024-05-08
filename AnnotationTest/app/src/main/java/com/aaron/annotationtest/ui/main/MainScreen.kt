package com.aaron.annotationtest.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aaron.annotationtest.ui.main.model.StableData

@Composable
fun MainScreen(
    vm: MainViewModel = viewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            var number by remember { mutableStateOf(0) }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
//                    onClick = vm::onClickInc,
                    onClick = {
                        number += 1
                    },
                    modifier = Modifier.weight(1F)
                ) {
                    Text(text = "+")
                }

                Spacer(modifier = Modifier.size(24.dp))

                Button(
//                    onClick = vm::onClickDec,
                    onClick = {
                        number -= 1
                    },
                    modifier = Modifier.weight(1F)
                ) {
                    Text(text = "-")
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            Text(text = "Stable inc/dec Hello Compose!")

            Spacer(modifier = Modifier.size(16.dp))

            ProgressText1(vm.stableData)

            Spacer(modifier = Modifier.size(16.dp))

            ProgressText2(number)
        }
    }
}

@Composable
fun ProgressText1(data: StableData) {
    Text(
        text = "progress: ${data.progress}"
    )
}

@Composable
fun ProgressText2(number: Int) {
    Text(
        text = "number: $number"
    )
}
