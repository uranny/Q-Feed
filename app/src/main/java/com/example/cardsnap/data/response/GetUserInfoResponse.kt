package com.example.cardsnap.data.response

import com.example.cardsnap.ui.adapter.item.Post

data class GetUserInfoResponse(
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

fun GetUserInfoResponse.toPost() : Post {
    return Post(
        this.id,
        this.uid,
        this.username,
        this.affiliation,
        this.grade,
        this.imageUrl,
        this.statusMessage,
        this.hashtags,
        this.age,
        this.height,
        this.weight,
        this.habbies,
        this.likes,
        this.dislikes,
        this.idealType,
        arrayListOf(),
        arrayListOf()
    )
}