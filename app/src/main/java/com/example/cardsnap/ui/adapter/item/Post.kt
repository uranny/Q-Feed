package com.example.cardsnap.adapter.item

import com.example.cardsnap.data.response.BaseUserResponse
import com.example.cardsnap.data.user.User

data class Post(
    override val id : Int = -1,
    override val uid : String = "string",
    override val username : String? = "string",
    override val affiliation : String? = "string",
    override val grade : String? = "string",
    override val imageUrl : String? = "string",
    override val statusMessage : String? = "string",
    override val hashtags : List<String>? = listOf(),
    override val age : Int? = 0,
    override val height : Int? = 0,
    override val weight : Int? = 0,
    override val habbies : String? = "string",
    override val likes : String? = "string",
    override val dislikes : String? = "string",
    override val idealType : String? = "string",
    val bookMarkLst : ArrayList<Int> = arrayListOf(),
    val chatLst : ArrayList<Cmt> = arrayListOf()
) : BaseUserResponse

fun Post.toUser() : User{
    return User(
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
        this.idealType
    )
}