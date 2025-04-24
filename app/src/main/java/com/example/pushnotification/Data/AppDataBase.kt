package com.example.pushnotification.Data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [NotificationEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "notification_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                // Use db instance that was just created
                                INSTANCE?.notificationDao()?.insertNotification(
                                    NotificationEntity(
                                        title = "Welcome!",
                                        body = "This is a preloaded notification.",
                                        data = "{\"source\":\"preload\"}"
                                    )
                                )
                                INSTANCE?.notificationDao()?.insertNotification(
                                    NotificationEntity(
                                        title = "Welcome!",
                                        body = "This is a preloaded notification.",
                                        data = "{\"source\":\"preload\"}"
                                    )
                                )
                                INSTANCE?.notificationDao()?.insertNotification(
                                    NotificationEntity(
                                        title = "Welcome!",
                                        body = "This is a preloaded notification.",
                                        data = "{\"source\":\"preload\"}"
                                    )
                                )
                                INSTANCE?.notificationDao()?.insertNotification(
                                    NotificationEntity(
                                        title = "Welcome!",
                                        body = "This is a preloaded notification.",
                                        data = "{\"source\":\"preload\"}"
                                    )
                                )
                            }
                        }
                    })

                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
