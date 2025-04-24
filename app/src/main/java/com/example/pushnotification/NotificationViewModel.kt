package com.example.pushnotification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pushnotification.Data.AppDatabase
import com.example.pushnotification.Data.NotificationEntity
import com.example.pushnotification.Data.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).notificationDao()
    private val repository = NotificationRepository(dao)

    private val _notifications = MutableStateFlow<List<NotificationEntity>>(emptyList())
    val notifications: StateFlow<List<NotificationEntity>> = _notifications

    init {
        viewModelScope.launch {
            _notifications.value = repository.getAllNotifications()
        }
    }
}

