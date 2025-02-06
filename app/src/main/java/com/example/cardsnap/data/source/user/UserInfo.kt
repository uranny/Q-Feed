package com.example.cardsnap.data.source.user

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.cardsnap.domain.entity.item.Chat
import com.example.cardsnap.domain.entity.response.SetProfileResponse
import com.example.cardsnap.domain.entity.item.ChatRoom
import com.example.cardsnap.domain.entity.item.Post
import com.example.cardsnap.domain.entity.response.BaseUserResponse

data class User(
    override val id: Int? = null,
    override val uid: String? = null,
    override val username: String? = null,
    override val affiliation: String? = null,
    override val grade: String? = null,
    override val imageUrl: String? = null,
    override val statusMessage: String? = null,
    override val hashtags: List<String>? = null,
    override val age: Int? = null,
    override val height: Int? = null,
    override val weight: Int? = null,
    override val habbies: String? = null,
    override val likes: String? = null,
    override val dislikes: String? = null,
    override val idealType: String? = null
) : BaseUserResponse

object UserInfo {
    var accessToken: String? = null
    var refreshToken: String? = null

    var myId: Int? = null
    var user = User()

    //recycleView 관련 리스트
    var postLst: ArrayList<Post> = arrayListOf()

    //recycleView 관련 리스트
    var chatRoomLst: ArrayList<ChatRoom> = arrayListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    var chatLst: ArrayList<Chat> = arrayListOf()

    fun resetUserInfo(){
        accessToken = null
        refreshToken = null
        postLst = arrayListOf()

        user = User()
    }

    fun updateUserInfo(getSUResponse: SetProfileResponse?){
        user = User(
            id = getSUResponse?.id,
            uid = getSUResponse?.uid,
            username = getSUResponse?.username,
            affiliation = getSUResponse?.affiliation ?: "대구소마고",
            grade = getSUResponse?.grade ?: "FIRST_GRADE",
            imageUrl = getSUResponse?.imageUrl,
            statusMessage = getSUResponse?.statusMessage,
            hashtags = getSUResponse?.hashtags ?: listOf("없어오"),
            age = getSUResponse?.age,
            height = getSUResponse?.height,
            weight = getSUResponse?.weight,
            habbies = getSUResponse?.habbies,
            likes = getSUResponse?.likes,
            dislikes = getSUResponse?.dislikes,
            idealType = getSUResponse?.idealType
        )
    }
}