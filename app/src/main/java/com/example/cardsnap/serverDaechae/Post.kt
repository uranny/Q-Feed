package com.example.cardsnap.serverDaechae

import android.net.Uri
import com.example.cardsnap.R

data class Post(
    var userName: String = "User",
    var userAffil: String = "Affiliation",
    var time: String = "2008-10-16",
    var list: ArrayList<String> = arrayListOf(),
    var userImg : String,
    var messagetxt: String = "Title",
    var tagTxt: String = "#HashTag",
    var age : Int? = 0,
    var height : Float? = 0.0f,
    var kg : Float? = 0.0f,
    var habbit  : String = "Habbit",
    var likeTxt : String = "likeThing",
    var hateTxt : String = "hateThing",
    var idealTxt : String = "idealPerson",
    val userId : String = "id"
)
