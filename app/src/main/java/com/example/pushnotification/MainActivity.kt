package com.example.pushnotification


import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestNotificationPermission()
        val navigateTo = intent.getStringExtra("navigateTo")


        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current.applicationContext as Application
            val viewModel: NotificationViewModel = viewModel(
                factory = NotificationViewModelFactory(context)
            )


            val callViewModel: CallEventViewModel = viewModel()



            LaunchedEffect(Unit) {
                CallEventBus.navigateToCall.collect {
                    Log.d("NAV", "Navigating to CALL screen")
                    navController.navigate(Routes.CALL)
                }
            }




            NavHost(navController = navController, startDestination = Routes.HOME) {
                composable(Routes.HOME) {
                    HomeScreen(navController)
                }
                composable(Routes.NOTIFICATION_LOG) {
                    NotificationLogScreen(viewModel)
                }
                composable(Routes.CALL) {
                    CallScreen()
                }
            }


//            val navController = rememberNavController()
//
//            LaunchedEffect(Unit) {
//                CallEventBus.navigateToCall.collect {
//                    navController.navigate(Routes.CALL)
//                }
//            }
//
//            NavHost(navController = navController, startDestination = Routes.HOME) {
//                composable(Routes.HOME) {
//                    HomeScreen(navController)
//                }
//                composable(Routes.CALL) {
//                    CallScreen()
//                }
//            }
        }

    }

//    object Routes {
//        const val HOME = "home"
//        const val CALL = "call"
//    }

    object Routes {
        const val HOME = "home"
        const val NOTIFICATION_LOG = "notification_log"
        const val CALL = "call"
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
