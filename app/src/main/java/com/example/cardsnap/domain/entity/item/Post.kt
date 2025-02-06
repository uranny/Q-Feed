package com.example.cardsnap.domain.entity.item

import com.example.cardsnap.domain.entity.response.BaseUserResponse
import com.example.cardsnap.data.source.user.User

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