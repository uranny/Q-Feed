package com.example.cardsnap.data.user

import android.provider.MediaStore
import com.example.cardsnap.serverDaechae.Chat
import com.example.cardsnap.serverDaechae.Post

object UserInfo{
    var userId : String = ""
    var accessToken : String? = null
    var refreshToken : String? = null
    var tokenType : String? = null
    var postPosition = 1
    var otherIndex = -1

    //recycleView 관련 리스트
    val chatLst = arrayListOf<ArrayList<Chat>>()
    var postLst : ArrayList<Post> = arrayListOf()

}

fun addPost(){
    val newPost = Post(
        "string${UserInfo.postPosition}",
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
        "1"
    )
    UserInfo.postPosition++
    UserInfo.postLst.add(newPost)
}