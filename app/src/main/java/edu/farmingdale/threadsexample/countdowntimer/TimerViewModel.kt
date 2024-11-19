package edu.farmingdale.threadsexample.countdowntimer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private var timerJob: Job? = null

    // Values selected in time picker
    var selectedHour by mutableIntStateOf(0)
        private set
    var selectedMinute by mutableIntStateOf(0)
        private set
    var selectedSecond by mutableIntStateOf(0)
        private set

    // Total milliseconds when timer starts
    var totalMillis by mutableLongStateOf(0L)
        private set

    // Time that remains
    var remainingMillis by mutableLongStateOf(0L)
        private set

    // Timer's running status
    var isRunning by mutableStateOf(false)
        private set

    // Select time from picker
    fun selectTime(hour: Int, min: Int, sec: Int) {
        selectedHour = hour.coerceIn(0, 99) // Limit hours to 0-99
        selectedMinute = min.coerceIn(0, 59) // Limit minutes to 0-59
        selectedSecond = sec.coerceIn(0, 59) // Limit seconds to 0-59
    }

    // Start the timer
    fun startTimer() {
        // Convert hours, minutes, and seconds to milliseconds
        totalMillis = ((selectedHour * 3600L) + (selectedMinute * 60L) + selectedSecond) * 1000L

        if (totalMillis > 0) {
            isRunning = true
            remainingMillis = totalMillis

            // Start coroutine for the countdown
            timerJob = viewModelScope.launch {
                while (remainingMillis > 0) {
                    delay(1000) // Wait 1 second
                    remainingMillis -= 1000
                }
                isRunning = false // Timer completes
            }
        }
    }

    // Cancel the timer
    fun cancelTimer() {
        timerJob?.cancel() // Stop the coroutine
        isRunning = false
        remainingMillis = 0L // Reset remaining time
    }

    // Reset the timer
    fun resetTimer() {
        timerJob?.cancel() // Stop any ongoing coroutine
        isRunning = false
        selectedHour = 0 // Reset selected values
        selectedMinute = 0
        selectedSecond = 0
        totalMillis = 0L // Reset total milliseconds
        remainingMillis = 0L // Reset remaining milliseconds
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel() // Clean up coroutine when ViewModel is destroyed
    }
}
