package com.example.cardsnap.data.base

data class LoginResponse(
    val accssToken: String,
    val refreshToken : String,
    val tokenType : String
)

data class RegisterResponse(
    val message: String,

)