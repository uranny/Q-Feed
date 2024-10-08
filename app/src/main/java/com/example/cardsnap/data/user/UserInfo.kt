package com.example.cardsnap.data.user

import android.provider.MediaStore
import com.example.cardsnap.serverDaechae.Chat
import com.example.cardsnap.serverDaechae.Post

object UserInfo{
    var accessToken : String? = null

    //recycleView 관련 리스트
    val chatLst = arrayListOf<ArrayList<Chat>>()
    val postLst : ArrayList<Post> =  arrayListOf(
        Post("string",
            "string",
            "string",
            arrayListOf(),
            "",
            "string",
            "string",
            1,
            1f,
            2f,
            "string",
            "string",
            "string",
            "string",
            "1"),
        Post("string",
            "string",
            "string",
            arrayListOf(),
            "",
            "string",
            "string",
            1,
            1f,
            2f,
            "string",
            "string",
            "string",
            "string",
            "1"),
        Post("string",
            "string",
            "string",
            arrayListOf(),
            "",
            "string",
            "string",
            1,
            1f,
            2f,
            "string",
            "string",
            "string",
            "string",
            "1")
    )

}