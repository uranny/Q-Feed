package com.example.cardsnap.data.repository

import android.util.Log
import com.example.cardsnap.ui.adapter.item.Post
import com.example.cardsnap.data.request.SetProfileRequest
import com.example.cardsnap.data.response.BaseUserResponse
import com.example.cardsnap.data.response.MyPageResponse
import com.example.cardsnap.data.response.toPost
import com.example.cardsnap.data.response.toUser
import com.example.cardsnap.data.source.base.UserDataSource
import com.example.cardsnap.data.source.remote.UserRemoteDataSource
import com.example.cardsnap.data.user.User
import com.example.cardsnap.data.user.UserInfo
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: UserDataSource
) {
    suspend fun uploadProfile(
        token : String,
        image: MultipartBody.Part
    ) : String {
        return kotlin.run {
            val response = remoteDataSource.uploadProfile(token, image)
            response.message
        }
    }

    suspend fun setProfile(
        token : String,
        setProfileRequest : SetProfileRequest
    ){
        kotlin.run {
            remoteDataSource.setProfile(token, setProfileRequest)
        }
    }

    private suspend fun getUserInfo(
        token : String,
        userId: Int
    ) : Post {
        return kotlin.run {
            remoteDataSource.getUserInfo(token, userId).toPost()
        }
    }

    suspend fun getMyPage(
        token : String,
    ){
        kotlin.run{
            val myPage = remoteDataSource.myPage(token)
            setMyPage(myPage)
        }
    }

    suspend fun getArticles(
        token : String,
    ){
        kotlin.run {
            val articleLst = remoteDataSource.articles(token)
            setArticles(token, articleLst)
            Log.d("GetArticle", "UserRepository.getArticles : ${UserInfo.postLst.size}")
        }
    }

    // 이거 부터 룸으로 바꿀 예정
    private fun setMyPage(
        myPage : MyPageResponse
    ){
        UserInfo.user = myPage.toUser()
    }

    private suspend fun setArticles(
        token : String,
        articleLst : List<Int>
    ) {
        kotlin.run {
            articleLst.forEach { item ->
                UserInfo.postLst.add(getUserInfo(token, item))
                Log.d("GetArticle", "UserRepository.setArticles : ${UserInfo.postLst.size}")
            }
        }
    }
}