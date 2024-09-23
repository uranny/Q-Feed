package com.example.cardsnap.data.auth

data class RegisterRequest(
    val userName : String,
    val password : String,
    val email : String
)