package com.example.pushnotification

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.sp
import com.example.pushnotification.Data.NotificationEntity
import com.example.pushnotification.NotificationViewModel



@Composable
fun NotificationLogScreen(
    viewModel: NotificationViewModel
) {
    val notifications = viewModel.notifications.collectAsState(initial = emptyList())
    Column {
        Text("Notification Log",
            modifier = Modifier
                .padding(16.dp),
            fontSize = 20.sp
        )
        LazyColumn {
            items(notifications.value) { notification ->
                NotificationItem(notification)
            }
        }
    }
}


@Composable
fun NotificationItem(notification: NotificationEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = notification.title ?: "No Title", fontWeight = FontWeight.Bold)
            Text(text = notification.body ?: "No Body")
            notification.data?.let {
                Text(text = "Data: $it", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


class NotificationViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotificationViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



