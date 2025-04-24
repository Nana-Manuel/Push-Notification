package com.example.pushnotification.Data


class NotificationRepository(private val dao: NotificationDao) {

    suspend fun insertNotification(notification: NotificationEntity) {
        dao.insertNotification(notification)
    }

    suspend fun getAllNotifications(): List<NotificationEntity> {
        return dao.getAllNotifications() // ✅ Use the correct name here
    }
}



