package com.sleep.timer.status

import com.sleep.timer.viewmodel.MainViewModel

class StartedStatus(private val viewModel: MainViewModel) : IStatus {
    override fun isStart(): Boolean {
        return true
    }

    override fun clickStartButton() {
        viewModel.timerController.pause()
    }

    override fun clickResetButton() {
        viewModel.timerController.reset()
    }
}