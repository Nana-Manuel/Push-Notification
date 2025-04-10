package com.example.pushnotification

import android.icu.text.CaseMap.Title

data class SendMessageDto(
    val to: String?,
    val notification: NotificationBody
)

data class NotificationBody(
    val title: String,
    val body: String
)