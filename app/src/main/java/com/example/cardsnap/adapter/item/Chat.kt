package com.example.cardsnap.adapter.`class`

import java.time.LocalDateTime

// 채팅방 안
data class Chat(
    val id : Int,
    val txt : String,
    val dateTime : LocalDateTime
)