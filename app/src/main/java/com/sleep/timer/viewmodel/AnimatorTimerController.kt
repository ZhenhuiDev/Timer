package com.sleep.timer.viewmodel

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import com.sleep.timer.status.InitialStatus
import com.sleep.timer.status.PausedStatus
import com.sleep.timer.status.StartedStatus
import com.sleep.timer.status.TimeSetStatus

class AnimatorTimerController(private val viewModel: MainViewModel) : ITimerController {

    private var valueAnimator: ValueAnimator? = null

    override fun start() {
        if (viewModel.totalTime == 0) return
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(viewModel.totalTime, 0)
            valueAnimator?.interpolator = LinearInterpolator()
            valueAnimator?.addUpdateListener {
                viewModel.timeLeft = it.animatedValue as Int
            }
            valueAnimator?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    viewModel.timeLeft = 0
                    viewModel.status = TimeSetStatus(viewModel)
                }
            })
        } else {
            valueAnimator?.setIntValues(viewModel.totalTime, 0)
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
        viewModel.timeLeft = 0
        if (viewModel.status is StartedStatus || viewModel.status is PausedStatus) {
            viewModel.status = TimeSetStatus(viewModel)
        } else {
            viewModel.totalTime = 0
            viewModel.status = InitialStatus()
        }
    }
}