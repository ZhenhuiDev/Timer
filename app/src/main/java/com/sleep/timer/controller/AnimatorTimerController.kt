package com.sleep.timer.controller

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.util.Log
import android.view.animation.LinearInterpolator
import com.sleep.timer.status.InitialStatus
import com.sleep.timer.status.PausedStatus
import com.sleep.timer.status.StartedStatus
import com.sleep.timer.status.TimeSetStatus
import com.sleep.timer.viewmodel.MainViewModel

class AnimatorTimerController(private val viewModel: MainViewModel) : ITimerController {

    companion object {
        private const val SPEED = 100
    }

    private var valueAnimator: ValueAnimator? = null

    override fun start() {
        if (viewModel.totalTime == 0) return
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(viewModel.totalTime * SPEED, 0)
            valueAnimator?.interpolator = LinearInterpolator()
            valueAnimator?.addUpdateListener {
                viewModel.timeLeft = it.animatedValue as Int / SPEED
                viewModel.timeLeftWithSpeed = it.animatedValue as Int / SPEED.toFloat()
                Log.d(
                    "AnimatorTimerController",
                    "viewModel.timeLeft = ${viewModel.timeLeft}, viewModel.timeLeftWithSpeed = ${viewModel.timeLeftWithSpeed}"
                )
            }
            valueAnimator?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    viewModel.timeLeft = viewModel.totalTime
                    viewModel.timeLeftWithSpeed = viewModel.totalTime.toFloat()
                    viewModel.status = TimeSetStatus(viewModel)
                }
            })
        } else {
            valueAnimator?.setIntValues(viewModel.totalTime * SPEED, 0)
        }
        valueAnimator?.duration = viewModel.totalTime * 1000L
        viewModel.status = StartedStatus(viewModel)
        valueAnimator?.start()
    }

    override fun pause() {
        valueAnimator?.pause()
        viewModel.status = PausedStatus(viewModel)
    }

    override fun resume() {
        valueAnimator?.resume()
        viewModel.status = StartedStatus(viewModel)
    }

    override fun reset() {
        valueAnimator?.removeAllListeners()
        valueAnimator?.cancel()
        valueAnimator = null
        if (viewModel.status is StartedStatus || viewModel.status is PausedStatus) {
            viewModel.timeLeft = viewModel.totalTime
            viewModel.timeLeftWithSpeed = viewModel.totalTime.toFloat()
            viewModel.status = TimeSetStatus(viewModel)
        } else {
            viewModel.totalTime = 0
            viewModel.timeLeft = 0
            viewModel.timeLeftWithSpeed = 0f
            viewModel.status = InitialStatus()
        }
    }
}