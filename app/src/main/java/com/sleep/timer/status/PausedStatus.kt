package com.sleep.timer.status

import com.sleep.timer.viewmodel.MainViewModel

class PausedStatus(private val viewModel: MainViewModel): IStatus {
    override fun isStart(): Boolean {
        return false
    }

    override fun clickStartButton() {
        viewModel.timerController.resume()
    }

    override fun clickResetButton() {
        viewModel.timerController.reset()
    }
}