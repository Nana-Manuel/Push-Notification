package com.example.pushnotification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.io.IOException

class ChatViewModel : ViewModel() {
    var state by mutableStateOf(ChatState())
        private set

    private val api: FcmApi = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create()

    fun onRemoteTokenChange(newToken: String){
        state = state.copy(
            remoteToken = newToken
        )
    }

    fun onSubmitRemoteToken(){
        state = state.copy(
            isEnteringToken = false
        )
    }

    fun onMessageChange(message: String){
        state = state.copy(
            messagingText = message
        )
    }

    fun sendMessage(isBroadCast: Boolean){
        viewModelScope.launch {
            val messageDto = SendMessageDto(
                to = if(isBroadCast) null else state.remoteToken,
                notification = NotificationBody(
                    title = "New Message!",
                    body = state.messagingText
                )
            )
            try{
                if(isBroadCast){
                    api.broadcast(messageDto)
                }else {
                    api.sendMessage(messageDto)
                }
                state = state.copy(
                    messagingText = ""
                )
            } catch (e: HttpException){
                e.printStackTrace()
            } catch (e: IOException){
                e.printStackTrace()
            }

        }
    }
}