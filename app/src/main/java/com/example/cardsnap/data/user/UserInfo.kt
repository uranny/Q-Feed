package com.example.cardsnap.data.user

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.cardsnap.adapter.item.Chat
import com.example.cardsnap.data.auth.RequestManager
import com.example.cardsnap.data.request.RefreshRequest
import com.example.cardsnap.data.response.SignupResponse
import com.example.cardsnap.data.request.SignupRequest
import com.example.cardsnap.adapter.item.ChatRoom
import com.example.cardsnap.adapter.item.Post

data class User(
    var id: Int? = null,
    var uid: String? = null,
    var username: String? = null,
    var affiliation: String? = null,
    var grade: String? = null,
    var imageUrl: String? = null,
    var statusMessage: String? = null,
    var hashtags: List<String>? = null,
    var age: Int? = null,
    var height: Int? = null,
    var weight: Int? = null,
    var habbies: String? = null,
    var likes: String? = null,
    var dislikes: String? = null,
    var idealType: String? = null
)

object UserInfo {
    var accessToken: String? = null
    var refreshToken: String? = null
    var tokenType: String? = null

    var otherIndex = -1
    var chatIndex: Int = -1

    var myId: Int? = null
    var userInfo = User()

    //recycleView 관련 리스트
    var postLst: ArrayList<Post> = arrayListOf()

    //recycleView 관련 리스트
    var chatRoomLst: ArrayList<ChatRoom> = arrayListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    var chatLst: ArrayList<Chat> = arrayListOf()

    fun resetUserInfo(){
        accessToken = null
        refreshToken = null
        tokenType = null
        postLst = arrayListOf()

        myId = null
    }

    fun updateUserInfo(getSUResponse: SignupResponse?){

        val arrayMap = mapOf(
            "FIRST_GRADE" to "1학년",
            "SECOND_GRADE" to "2학년",
            "THIRD_GRADE" to "3학년",
        )
        myId = getSUResponse?.id

        userInfo = User(
            id = getSUResponse?.id,
            uid = getSUResponse?.uid,
            username = getSUResponse?.username,
            affiliation = getSUResponse?.affiliation ?: "대구소마고",
            grade = arrayMap[getSUResponse?.grade] ?: "1학년",
            imageUrl = getSUResponse?.imageUrl,
            statusMessage = getSUResponse?.statusMessage,
            hashtags = getSUResponse?.hashtags ?: listOf("없어오"),
            age = getSUResponse?.age,
            height = getSUResponse?.height,
            weight = getSUResponse?.weight,
            habbies = getSUResponse?.hobbies,
            likes = getSUResponse?.likes,
            dislikes = getSUResponse?.dislikes,
            idealType = getSUResponse?.idealType
        )
    }

    suspend fun retrySignUp(signUpdata : SignupRequest, refreshData : RefreshRequest){
        val refreshResponse = RequestManager.refreshRequest(refreshData).body()?.data

        accessToken = refreshResponse?.accessToken
        refreshToken = refreshResponse?.refreshToken
        tokenType = refreshResponse?.tokenType

        val getSUResponse = RequestManager.signupRequest("${tokenType!!} ${accessToken!!}", signUpdata).body()
        updateUserInfo(getSUResponse)
    }
}