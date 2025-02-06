package com.example.cardsnap.domain.entity.item

// 채팅방
data class ChatRoom(
    val userName: String = "User",
    val userImg : String,
    val userAffil: String = "Affiliation",
    val userId: String = "User",
    val time : String = "10:16",
    val lastChat: String? = null,
)