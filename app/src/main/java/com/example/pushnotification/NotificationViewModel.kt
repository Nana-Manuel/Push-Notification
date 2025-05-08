package com.example.pushnotification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pushnotification.NotificationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotificationViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).notificationDao()
    private val repository = NotificationRepository(dao)

    val notifications = repository.notifications

    fun deleteNotification(notification: NotificationEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNotification(notification)
        }
    }

}

