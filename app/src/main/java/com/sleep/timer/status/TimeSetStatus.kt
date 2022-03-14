package com.sleep.timer.status

import com.sleep.timer.viewmodel.MainViewModel

class TimeSetStatus(private val viewModel: MainViewModel) : IStatus {
    override fun isStart(): Boolean {
        return false
    }

    override fun clickStartButton() {
        // start
        viewModel.timerController.start()
    }

    override fun clickResetButton() {
        viewModel.timerController.reset()
    }
}