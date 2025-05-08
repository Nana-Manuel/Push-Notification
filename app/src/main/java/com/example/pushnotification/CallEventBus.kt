package com.example.pushnotification

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object CallEventBus {
    private val _navigateToCall = MutableSharedFlow<Unit>(replay = 0)
    val navigateToCall = _navigateToCall.asSharedFlow()

    suspend fun triggerNavigateToCall() {
        _navigateToCall.emit(Unit)
    }
}
