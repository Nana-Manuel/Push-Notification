package com.example.pushnotification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CallEventViewModel : ViewModel() {
    private val _navigateToLog = MutableSharedFlow<Boolean>(replay = 0)
    val navigateToLog = _navigateToLog.asSharedFlow()

    fun triggerNavigation() {
        viewModelScope.launch {
            _navigateToLog.emit(true)
        }
    }
}
