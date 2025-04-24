package com.example.pushnotification.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String?,
    val body: String?,
    val data: String?,         // Save data map as JSON string
    val receivedAt: Long = System.currentTimeMillis()
)

