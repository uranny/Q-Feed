package com.example.cardsnap.data.base

data class LoginResponse(
    val status: Int,
    val state : String,
    val message : String,
    val data : TokenData
)

data class TokenData(
    val accessToken : String,
    val refreshToken : String,
    val tokenType : String
)

data class RegisterResponse(
    val status : Int,
    val state : String,
    val message: String
)

data class SignupResponse(
    val id : Int,
    val uid : String,
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

data class GetUserInfoResponse(
    val id : Int,
    val uid : String,
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

data class UploadProfileResponse(
    val status: Int,
    val state : String,
    val message : String,
    val data : TokenData
)

data class MyPageResponse(
    val status : Int,
    val state : String,
    val message: String
)