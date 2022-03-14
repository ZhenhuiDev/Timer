package com.sleep.timer.status

class InitialStatus : IStatus {
    override fun isStart(): Boolean {
        return false
    }

    override fun clickStartButton() {
        // do nothing
    }

    override fun clickResetButton() {
        // do nothing
    }

    override fun resetButtonEnabled(): Boolean {
        return false
    }

    override fun startButtonEnabled(): Boolean {
        return false
    }
}