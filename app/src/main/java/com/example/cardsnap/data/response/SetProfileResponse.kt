package com.example.cardsnap.data.response

data class SetProfileResponse(
    override val id : Int,
    override val uid : String,
    override val username : String,
    override val affiliation : String,
    override val grade : String,
    override val imageUrl : String,
    override val statusMessage : String,
    override val hashtags : List<String>,
    override val age : Int,
    override val height : Int,
    override val weight : Int,
    override val habbies : String,
    override val likes : String,
    override val dislikes : String,
    override val idealType : String
) : BaseUserResponse
