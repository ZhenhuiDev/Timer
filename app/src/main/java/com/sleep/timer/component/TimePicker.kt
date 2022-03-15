package com.sleep.timer.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimePicker(initialTime: Int, onTimeChange: (Int) -> Unit) {
    Log.d("TimePicker", "initial time = ${initialTime}")
    var seconds = initialTime % 60
    var minutes = (initialTime / 60) % 60
    var hours = initialTime / 60 / 60
    val format = { value: Int -> String.format("%02d", value) }
    Row(
        Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ListPicker(hours, data = List(100) { it }, onValueChange = {
            // hour
            hours = it
            onTimeChange(hours * 60 * 60 + minutes * 60 + seconds)
        }, format)
        Text(text = ":", Modifier.padding(horizontal = 10.dp))
        ListPicker(minutes, data = List(60) { it }, onValueChange = {
            // minute
            minutes = it
            onTimeChange(hours * 60 * 60 + minutes * 60 + seconds)
        }, format)
        Text(text = ":", Modifier.padding(horizontal = 10.dp))
        ListPicker(seconds, data = List(60) { it }, onValueChange = {
            // second
            seconds = it
            onTimeChange(hours * 60 * 60 + minutes * 60 + seconds)
        }, format)
    }
}
