package com.sleep.timer.status

interface IStatus {
    fun isStart(): Boolean
    fun clickStartButton()
    fun clickResetButton()
    fun resetButtonEnabled(): Boolean {
        return true
    }

    fun startButtonEnabled(): Boolean {
        return true
    }
}