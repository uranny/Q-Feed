package com.example.cardsnap.data.user

import android.provider.MediaStore
import com.example.cardsnap.data.auth.AuthRequestManager
import com.example.cardsnap.data.auth.RefreshRequest
import com.example.cardsnap.data.base.SignupResponse
import com.example.cardsnap.data.user.request.SignupRequest
import com.example.cardsnap.serverDaechae.Chat
import com.example.cardsnap.serverDaechae.Post

object UserInfo{

    var accessToken : String? = null
    var refreshToken : String? = null
    var tokenType : String? = null
    var postPosition = 1
    var otherIndex = -1

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
    val chatLst = arrayListOf<ArrayList<Chat>>()
    var postLst : ArrayList<Post> = arrayListOf()
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