package com.example.cardsnap.serverDaechae

import android.net.Uri
import com.example.cardsnap.R

class Chat(
    val userName: String = "User",
    val userImg : String,
    val userAffil: String = "Affiliation",
    val userId: String = "User",
    val time : String = "10:16",
    val lastChat: String = "Title",
    val chatList: ArrayList<String> = arrayListOf()
)