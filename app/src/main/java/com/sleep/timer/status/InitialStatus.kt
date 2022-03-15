package com.sleep.timer.status

import com.sleep.timer.viewmodel.MainViewModel

class InitialStatus(private val viewModel: MainViewModel) : IStatus {
    override fun isStart(): Boolean {
        return false
    }

    override fun clickStartButton() {
        // do nothing
        viewModel.timerController.start()
    }

    override fun clickResetButton() {
        // do nothing
        viewModel.timerController.reset()
    }

    override fun resetButtonEnabled(): Boolean {
        return false
    }

    override fun startButtonEnabled(): Boolean {
        return false
    }

    override fun progress(): Float {
        return 0f
    }
}