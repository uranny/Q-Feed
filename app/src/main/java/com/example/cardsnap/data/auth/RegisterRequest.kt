package com.example.cardsnap.data.auth

data class RegisterRequest(
    val username : String,
    val password : String,
    val email : String
)