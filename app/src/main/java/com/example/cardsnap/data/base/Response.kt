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

data class RefreshResponse(
    val status : Int,
    val state : String,
    val message: String,
    val data : TokenData
)

data class SignupResponse(
    val id : Int,
    val uid : String,
    val username : String,
    val affiliation : String,
    val grade : String,
    val imageUrl : String,
    val statusMessage : String,
    val hashtags : List<String>,
    val age : Int,
    val height : Int,
    val weight : Int,
    val hobbies : String,
    val likes : String,
    val dislikes : String,
    val idealType : String
)

data class GetUserInfoResponse(
    val id : Int,
    val uid : String,
    val username : String,
    val affiliation : String,
    val grade : String,
    val imageUrl : String,
    val statusMessage : String,
    val hashtags : List<String>,
    val age : Int,
    val height : Int,
    val weight : Int,
    val hobbies : String,
    val likes : String,
    val dislikes : String,
    val idealType : String
)

data class UploadProfileResponse(
    val status: Int,
    val state : String,
    val message : String,
    val data : List<String>
)

data class MyPageResponse(
    val id : Int,
    val uid : String,
    val username : String,
    val affiliation : String,
    val grade : String,
    val imageUrl : String,
    val statusMessage : String,
    val hashtags : List<String>,
    val age : Int,
    val height : Int,
    val weight : Int,
    val hobbies : String,
    val likes : String,
    val dislikes : String,
    val idealType : String
)