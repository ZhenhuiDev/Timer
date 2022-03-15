package com.sleep.timer.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Clock(radius: Dp, progress: Float) {
    val storkWidth = 10.dp
    Box(
        modifier = Modifier.size(radius * 2 + storkWidth),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(radius * 2), onDraw = {
            // draw gray dial
            drawCircle(
                brush = Brush.sweepGradient(
                    0f to Color(0xFFD1D2D6),
                    0.25f to Color(0xFFE7E9EB),
                    0.5f to Color(0xFFD1D2D6),
                    0.75f to Color(0xFFB8BACB),
                    1f to Color(0xFFD1D2D6)
                ),
                style = Stroke(
                    width = storkWidth.toPx(),
                    pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(4f, 8f))
                ),
                alpha = 0.8f,
            )

            // draw progress dial
            drawArc(
                brush = Brush.sweepGradient(
                    0f to Color(0xFF9AC2E6),
                    0.75f to Color(0xFF1D21ED),
                    0.75f to Color.Transparent,
                    1f to Color(0xFF9AC2E6),
                ),
                startAngle = -90f,
                sweepAngle = progress,
                useCenter = false,
                style = Stroke(
                    width = storkWidth.toPx(),
                    pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(4f, 8f))
                ),
            )

            // draw pointer
            val angle = (360 - progress) / 180 * Math.PI
            val pointTailLength = 8.dp.toPx()
            drawLine(
                color = Color.Red,
                start = Offset(
                    radius.toPx() + pointTailLength * sin(angle).toFloat(),
                    radius.toPx() + pointTailLength * cos(angle).toFloat()
                ),
                end = Offset(
                    (radius.toPx() - radius.toPx() * sin(angle) - sin(angle) * storkWidth.toPx() / 2).toFloat(),
                    (radius.toPx() - radius.toPx() * cos(angle) - cos(angle) * storkWidth.toPx() / 2).toFloat(),
                ),
                strokeWidth = 2.dp.toPx()
            )
            drawCircle(
                color = Color.Red,
                radius = 5.dp.toPx()
            )
            drawCircle(
                color = Color.White,
                radius = 3.dp.toPx()
            )
        })
    }
}