package com.aaron.sample.chapter41

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aaron.sample.chapter41.ui.theme.Chapter41ViewModelDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Chapter41ViewModelDemoTheme {
                val vm: DemoViewModel = viewModel()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(
                        isFahrenheit = vm.isFahrenheit,
                        result = vm.result,
                        convertTemp = {
                            vm.convertTemp(it)
                        },
                        switchChange = {
                            vm.switchChange()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    isFahrenheit: Boolean,
    result: String,
    convertTemp: (String) -> Unit,
    switchChange: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var textState by remember { mutableStateOf("") }
        val onTextChange = { text: String ->
            textState = text
        }

        Text(
            text = "Temperature Converter",
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.h4
        )

        InputRow(
            isFahrenheit = isFahrenheit,
            textState = textState,
            switchChange = switchChange,
            onTextChange = onTextChange
        )

        Text(
            text = result,
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.h3
        )

        Button(onClick = { convertTemp(textState) }) {
            Text(text = "Convert Temperature")
        }
    }
}

@Composable
fun InputRow(
    isFahrenheit: Boolean,
    textState: String,
    switchChange: () -> Unit,
    onTextChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(checked = isFahrenheit, onCheckedChange = { switchChange() })
        OutlinedTextField(
            value = textState,
            onValueChange = {
                onTextChange(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            label = {
                Text("Enter temperature")
            },
            modifier = Modifier.padding(10.dp),
            textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_ac_unit),
                    contentDescription = "frost",
                    modifier = Modifier.size(40.dp)
                )
            }
        )
        Crossfade(
            targetState = isFahrenheit,
            animationSpec = tween()
        ) { visible ->
            when (visible) {
                true -> Text("\u2109", style = MaterialTheme.typography.h4)
                else -> Text("\u2103", style = MaterialTheme.typography.h4)
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview(vm: DemoViewModel = DemoViewModel()) {
    Chapter41ViewModelDemoTheme {
        MainScreen(
            isFahrenheit = vm.isFahrenheit,
            result = vm.result,
            convertTemp = {
                vm.convertTemp(it)
            },
            switchChange = {
                vm.switchChange()
            }
        )
    }
}