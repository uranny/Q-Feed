package com.example.cardsnap.data.response

import com.example.cardsnap.data.user.User

interface BaseUserResponse {
    val id: Int?
    val uid: String?
    val username: String?
    val affiliation: String?
    val grade: String?
    val imageUrl: String?
    val statusMessage: String?
    val hashtags: List<String>?
    val age: Int?
    val height: Int?
    val weight: Int?
    val habbies: String?
    val likes: String?
    val dislikes: String?
    val idealType: String?
}