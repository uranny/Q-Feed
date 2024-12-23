package com.example.cardsnap.data.user

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.cardsnap.adapter.adapter_class.Chat
import com.example.cardsnap.data.auth.AuthRequestManager
import com.example.cardsnap.data.auth.request.RefreshRequest
import com.example.cardsnap.data.base.SignupResponse
import com.example.cardsnap.data.user.request.SignupRequest
import com.example.cardsnap.adapter.adapter_class.ChatRoom
import com.example.cardsnap.adapter.adapter_class.Cmt
import com.example.cardsnap.adapter.adapter_class.Post
import java.time.LocalDateTime

object UserInfo{

    var accessToken : String? = null
    var refreshToken : String? = null
    var tokenType : String? = null

    var postPosition = 1
    var otherIndex = -1
    var chatIndex : Int = -1

    var myId : Int? = null

    var id : Int? = null
    var uid : String? = null
    var usernname : String? = null
    var affiliation : String? = null
    var grade : String? = null
    var imageUrl : String? = null
    var statusMessage : String? = null
    var hashtags : List<String> = listOf()
    var age :  Int? = null
    var height : Int? = null
    var weight : Int? = null
    var habbies : String? = null
    var likes : String? = null
    var dislikes : String? = null
    var idealType : String? = null

    //recycleView 관련 리스트
    var postLst : ArrayList<Post> = arrayListOf()

    //recycleView 관련 리스트
    var chatRoomLst : ArrayList<ChatRoom> = arrayListOf<ChatRoom>(
        ChatRoom(
            "uranny",
            "string",
            "대구소마고 1학년",
            "0",
            "10:16",
            "몰라"
        )
    )

    @RequiresApi(Build.VERSION_CODES.O)
    var chatLst : ArrayList<Chat> = arrayListOf<Chat>(
        Chat(0,
            "안녕 내 친구",
            LocalDateTime.of(2024, 11, 28, 10, 10, 10)
        ),
        Chat(0,
            "내 이름은 오정민이야 반가우이!",
            LocalDateTime.of(2024, 11, 28, 10, 10, 10)
        )
    )

    @RequiresApi(Build.VERSION_CODES.O)
    var cmtLst = arrayListOf<Cmt>(
        Cmt(
            "user",
            "몰라요",
            "구지중",
            "1년전",
            LocalDateTime.of(2023, 8, 10, 10, 10)
        ),
        Cmt(
            "user",
            "몰라요",
            "구지중",
            "3달 전",
            LocalDateTime.of(2024, 9, 1, 10, 10)
        ),
        Cmt(
            "user",
            "몰라요",
            "구지중",
            "5일 전",
            LocalDateTime.of(2024, 11, 24, 10, 10)
        ),
        Cmt(
            "user",
            "몰라요",
            "구지중",
            "1시간 전",
            LocalDateTime.of(2024, 11, 29, 13, 20)
        ), Cmt(
            "user",
            "몰라요",
            "구지중",
            "몇 분 전",
            LocalDateTime.of(2024, 11, 28, 14, 20)
        )
    )
}

fun resetUserInfo(){
    with(UserInfo){

        accessToken = null
        refreshToken = null
        tokenType = null

        myId = null
        id = null
        uid = null
        usernname = null
        affiliation = null
        grade = null
        imageUrl = null
        statusMessage = null
        hashtags = listOf()
        age = null
        height = null
        weight = null
        habbies = null
        likes = null
        dislikes = null
        idealType = null
    }
}

fun updateUserInfo(getSUResponse: SignupResponse?){

    val arrayMap = mapOf(
        "FIRST_GRADE" to "1학년",
        "SECOND_GRADE" to "2학년",
        "THIRD_GRADE" to "3학년"
    )

    with(UserInfo){
        myId = getSUResponse?.id
        id = getSUResponse?.id
        uid = getSUResponse?.uid
        usernname = getSUResponse?.username
        affiliation = getSUResponse?.affiliation
        grade = arrayMap[getSUResponse?.grade]
        imageUrl = getSUResponse?.imageUrl
        statusMessage = getSUResponse?.statusMessage
        hashtags = getSUResponse?.hashtags ?: listOf("없어오")
        age = getSUResponse?.age
        height = getSUResponse?.height
        weight = getSUResponse?.weight
        habbies = getSUResponse?.hobbies
        likes = getSUResponse?.likes
        dislikes = getSUResponse?.dislikes
        idealType = getSUResponse?.idealType
    }
}

suspend fun retrySignUp(signUpdata : SignupRequest, refreshData : RefreshRequest){
    val refreshResponse = AuthRequestManager.refreshRequest(refreshData).body()?.data

    UserInfo.accessToken = refreshResponse?.accessToken
    UserInfo.refreshToken = refreshResponse?.refreshToken
    UserInfo.tokenType = refreshResponse?.tokenType

    val getSUResponse = UserRequestManager.signupRequest("${UserInfo.tokenType!!} ${UserInfo.accessToken!!}", signUpdata).body()
    updateUserInfo(getSUResponse)
}


fun addPost(){
    val newPost = Post(
        UserInfo.postPosition,
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