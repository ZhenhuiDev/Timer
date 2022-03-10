package com.sleep.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sleep.timer.component.BottomController
import com.sleep.timer.component.Dial
import com.sleep.timer.component.TimePicker
import com.sleep.timer.ui.theme.TimerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            TimerTheme {
                val useDarkIcons = MaterialTheme.colors.isLight
                SideEffect {
                    systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = useDarkIcons)
                }
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
                    TimerApp()
                }
            }
        }
    }
}

@Composable
fun TimerApp() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Dial()
        TimePicker()
        BottomController()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TimerTheme {
        TimerApp()
    }
}