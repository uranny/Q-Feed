package com.example.cardsnap.data.user.request

data class SignupRequest(
    val usernname : String,
    val affiliation : String,
    val imageUrl : String,
    val statusMessage : String,
    val hashtags : List<String>,
    val age : Int,
    val height : Int,
    val weight : Int,
    val habbies : String,
    val likes : String,
    val dislikes : String,
    val idealType : String
)