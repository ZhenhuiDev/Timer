package com.sleep.timer.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.sleep.timer.status.IStatus
import com.sleep.timer.status.InitialStatus
import com.sleep.timer.viewmodel.MainViewModel

@Composable
fun BottomController(viewModel: MainViewModel, onStartClick: () -> Unit, onResetClick: () -> Unit) {
    Row(Modifier.padding(bottom = 50.dp)) {
        val enable = viewModel.totalTime != 0
        // reset button
        IconButton(
            icon = Icons.Rounded.Refresh,
            enable = enable,
            iconColor = if (enable) Color.Black else Color(0xFFC6C7C9),
            backgroundColor = if (enable) Color.White else Color(0xFFF7F8FA),
            onclick = onResetClick
        )

        Spacer(modifier = Modifier.size(40.dp))

        // play button
        IconButton(
            icon = if (viewModel.status.isStart()) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
            enable = enable,
            iconColor = if (enable) Color.White else Color(0xFFC2D1F5),
            backgroundColor = if (enable) Color(0xFF225AEE) else Color(0xFF9AB6F1),
            onclick = onStartClick
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IconButton(
    icon: ImageVector,
    backgroundColor: Color,
    iconColor: Color,
    enable: Boolean = true,
    onclick: () -> Unit
) {
    Surface(
        color = backgroundColor,
        shape = CircleShape,
        enabled = enable,
        onClick = onclick,
        elevation = 3.dp,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier
                .padding(5.dp)
                .size(30.dp)
        )
    }
}