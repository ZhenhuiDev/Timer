package com.sleep.timer.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun <T> ListPicker(value: T, data: List<T>, onValueChange: (newValue: T) -> Unit, format: (value: T) -> String) {
    val minimumAlpha = 0.3f
    val dataHeight = 30.dp
    val listHeight = dataHeight * 3
    val halfListHeightPx = with(LocalDensity.current) { listHeight.toPx() / 2 }
    val coroutineScope = rememberCoroutineScope()
    val animatedOffset = remember { Animatable(0f) }
    val indexOfElement = getItemIndexForOffset(data, value, animatedOffset.value, halfListHeightPx)
    val coercedAnimatedOffset = animatedOffset.value % halfListHeightPx

    Box(
        modifier = Modifier
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState(onDelta = {
                    coroutineScope.launch {
                        animatedOffset.snapTo(animatedOffset.value + it)
                    }
                }),
                onDragStopped = { velocity ->
                    coroutineScope.launch {
                        val endValue = animatedOffset.fling(
                            initialVelocity = velocity,
                            animationSpec = exponentialDecay(frictionMultiplier = 20f),
                            adjustTarget = { target ->
                                val coercedTarget = target % halfListHeightPx
                                val coercedAnchors = listOf(-halfListHeightPx, 0f, halfListHeightPx)
                                val coercedPoint = coercedAnchors.minByOrNull { abs(it - coercedTarget) }!!
                                val base = halfListHeightPx * (target / halfListHeightPx).toInt()
                                coercedPoint + base
                            }
                        ).endState.value
                        val result = data[getItemIndexForOffset(data, value, endValue, halfListHeightPx)]
                        onValueChange(result)
                        animatedOffset.snapTo(0f)
                    }
                }
            ),
    ) {
        Box(
            modifier = Modifier.offset { IntOffset(x = 0, y = coercedAnimatedOffset.roundToInt()) }
        ) {
            Row(
                Modifier
                    .offset(y = -listHeight / 2)
                    .height(dataHeight),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = format(data[getIndexSafely(data.size, indexOfElement, -1)]),
                    modifier = Modifier.alpha(maxOf(minimumAlpha, coercedAnimatedOffset / halfListHeightPx)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Row(
                Modifier.height(dataHeight),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = format(data[indexOfElement]),
                    modifier = Modifier.alpha((maxOf(minimumAlpha, 1 - abs(coercedAnimatedOffset) / halfListHeightPx))),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Row(
                Modifier
                    .offset(y = listHeight / 2)
                    .height(dataHeight),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = format(data[getIndexSafely(data.size, indexOfElement, 1)]),
                    modifier = Modifier.alpha(maxOf(minimumAlpha, -coercedAnimatedOffset / halfListHeightPx)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }
}

private fun <T> getItemIndexForOffset(
    data: List<T>,
    item: T,
    offset: Float,
    halfListHeightPx: Float
): Int {
    return getIndexSafely(data.size, data.indexOf(item), -(offset / halfListHeightPx).toInt())
}

private fun getIndexSafely(total: Int, currentIndex: Int, offset: Int): Int {
    if (offset == 0) return currentIndex
    val originIndex = (currentIndex + offset) % total
    if (originIndex < 0) {
        return total + originIndex
    }
    return originIndex
}

private suspend fun Animatable<Float, AnimationVector1D>.fling(
    initialVelocity: Float,
    animationSpec: DecayAnimationSpec<Float>,
    adjustTarget: ((Float) -> Float)?,
    block: (Animatable<Float, AnimationVector1D>.() -> Unit)? = null,
): AnimationResult<Float, AnimationVector1D> {
    val targetValue = animationSpec.calculateTargetValue(value, initialVelocity)
    val adjustedTarget = adjustTarget?.invoke(targetValue)
    return if (adjustedTarget != null) {
        animateTo(
            targetValue = adjustedTarget,
            initialVelocity = initialVelocity,
            block = block
        )
    } else {
        animateDecay(
            initialVelocity = initialVelocity,
            animationSpec = animationSpec,
            block = block,
        )
    }
}