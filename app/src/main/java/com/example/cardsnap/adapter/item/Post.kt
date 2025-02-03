package com.example.cardsnap.adapter.`class`

data class Post(
    val id : Int = -1,
    val uid : String = "string",
    val username : String? = "string",
    val affiliation : String? = "string",
    val grade : String? = "string",
    val imageUrl : String? = "string",
    val statusMessage : String? = "string",
    val hashtags : List<String>? = listOf(),
    val age : Int? = 0,
    val height : Int? = 0,
    val weight : Int? = 0,
    val hobbies : String? = "string",
    val likes : String? = "string",
    val dislikes : String? = "string",
    val idealType : String? = "string",
    val bookMarkLst : ArrayList<Int>,
    val chatLst : ArrayList<Cmt>
)
