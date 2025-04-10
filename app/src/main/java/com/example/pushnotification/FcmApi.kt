package com.example.pushnotification

import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {
    @POST("/send")
    suspend fun sendMessage(
        @Body body: SendMessageDto
    )

    suspend fun broadcast(
        @Body body: SendMessageDto
    )
}