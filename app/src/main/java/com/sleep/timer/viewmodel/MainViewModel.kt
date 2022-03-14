package com.sleep.timer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sleep.timer.status.IStatus
import com.sleep.timer.status.InitialStatus

class MainViewModel : ViewModel() {
    // second
    var totalTime by mutableStateOf(0)

    // second
    var timeLeft by mutableStateOf(0)
    var status: IStatus by mutableStateOf(InitialStatus())
    val timerController: ITimerController = AnimatorTimerController(this)

    override fun onCleared() {
        super.onCleared()
        totalTime = 0
        timeLeft = 0
        timerController.reset()
    }

    fun startTemp() {
        totalTime = 10
        timeLeft = 0
        timerController.start()
    }
}