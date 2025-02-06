package com.example.cardsnap.domain.entity.response

data class TokenData(
    val accessToken : String,
    val refreshToken : String,
    val tokenType : String
)
