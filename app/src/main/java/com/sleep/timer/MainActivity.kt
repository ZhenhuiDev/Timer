package com.sleep.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sleep.timer.component.BottomController
import com.sleep.timer.component.Clock
import com.sleep.timer.ui.theme.TimerTheme
import com.sleep.timer.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

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
        Spacer(modifier = Modifier.size(60.dp))
        Clock(100.dp, viewModel.status.progress())
        Spacer(modifier = Modifier.size(30.dp))
        Text(
            text = viewModel.timeLeft.toFormatTime(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.SansSerif
        )
        // TODO time picker
        Column(Modifier.weight(1f)) {
            Spacer(modifier = Modifier.size(20.dp))
            TextButton(onClick = { viewModel.startTemp() }) {
                Text(text = "start")
            }
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

fun Int.toFormatTime(): String {
    val seconds = this % 60
    val minutes = (this / 60) % 60
    val hours = (this / 60 / 60) % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}