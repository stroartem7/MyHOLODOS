package com.example.myholodos.api

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

data class Message(
    val role: String,
    val text: String
)

data class CompletionOptions(
    val stream: Boolean = false,
    val temperature: Double = 0.7,
    val maxTokens: String = "200"
)

data class ChatRequest(
    val modelUri: String,
    val completionOptions: CompletionOptions,
    val messages: List<Message>
)

data class Alternative(
    val message: Message
)

data class Result(
    val alternatives: List<Alternative>
)

data class ChatResponse(
    val result: Result
)

interface OpenAIService {

    @POST("foundationModels/v1/completion")
    suspend fun getRecommendation(
        @Header("Authorization") auth: String,
        @Header("x-folder-id") folderId: String,
        @Body request: ChatRequest
    ): ChatResponse
}