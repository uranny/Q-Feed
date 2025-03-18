package com.example.cardsnap.data.repository

import com.example.cardsnap.domain.entity.item.Post
import com.example.cardsnap.domain.entity.request.SetProfileRequest
import com.example.cardsnap.domain.entity.response.MyPageResponse
import com.example.cardsnap.data.source.base.UserDataSource
import com.example.cardsnap.data.source.user.UserInfo
import com.example.cardsnap.domain.mapper.toPost
import com.example.cardsnap.domain.mapper.toUser
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
            articleLst.map { item ->
                UserInfo.postLst.add(getUserInfo(token, item))
            }
        }
    }
}