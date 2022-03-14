package com.sleep.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sleep.timer.component.BottomController
import com.sleep.timer.component.Dial
import com.sleep.timer.ui.theme.TimerTheme
import com.sleep.timer.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            TimerTheme {
                val useDarkIcons = MaterialTheme.colors.isLight
                SideEffect {
                    systemUiController.setSystemBarsColor(Color(0xFFF3F3F5), darkIcons = useDarkIcons)
                }
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF3F3F5)) {
                    TimerApp()
                }
            }
        }
    }
}

@Composable
fun TimerApp() {
    val viewModel: MainViewModel = viewModel()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Column(Modifier.weight(1f)) {
            Dial()
        }
        Column(Modifier.weight(1f)) {
            TextButton(onClick = { viewModel.startTemp() }) {
                Text(text = "test")
            }
            Text(text = "total: ${viewModel.totalTime}, left: ${viewModel.timeLeft}")
        }
        BottomController(
            status = viewModel.status,
            onStartClick = {
                viewModel.status.clickStartButton()
            },
            onResetClick = {
                viewModel.status.clickResetButton()
            })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TimerTheme {
        TimerApp()
    }
}