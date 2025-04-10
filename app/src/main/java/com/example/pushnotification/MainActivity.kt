package com.example.pushnotification

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pushnotification.ui.theme.PushnotificationTheme
import android.Manifest
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : ComponentActivity() {
    private val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission() // Now works fine!

        FirebaseApp.initializeApp(this)

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("FCM", "FCM Token: $token")
                } else {
                    Log.w("FCM", "Fetching FCM token failed", task.exception)
                }
            }

        enableEdgeToEdge()
        setContent {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxSize()
            ) {
                val state = viewModel.state
                if (state.isEnteringToken) {
                    EnterTokenDialog(
                        token = state.remoteToken,
                        onTokenChange = viewModel::onRemoteTokenChange,
                        onSubmit = viewModel::onSubmitRemoteToken
                    )
                } else {
                    ChatScreen(
                        messageText = state.messagingText,
                        onMessageSend = {
                            viewModel.sendMessage(isBroadCast = false)
                        },
                        onMessageBroadcast = {
                            viewModel.sendMessage(isBroadCast = true)
                        },
                        onMessageChange = viewModel::onMessageChange
                    )
                }
            }
        }
    }


    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PushnotificationTheme {

    }
}