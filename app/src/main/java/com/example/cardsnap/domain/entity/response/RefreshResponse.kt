package com.example.cardsnap.domain.entity.response

data class RefreshResponse(
    val status : Int,
    val state : String,
    val message: String,
    val data : TokenData
)

