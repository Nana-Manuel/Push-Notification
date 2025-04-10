package com.example.pushnotification

data class ChatState(
    val isEnteringToken: Boolean = true,
    val remoteToken: String = "",
    val messagingText: String = ""
)
