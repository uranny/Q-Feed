package com.example.cardsnap.domain.usecase

import android.util.Log
import com.example.cardsnap.data.repository.AuthRepository
import com.example.cardsnap.data.repository.UserRepository
import com.example.cardsnap.data.source.user.UserInfo
import com.example.cardsnap.domain.entity.request.RefreshRequest
import javax.inject.Inject

// GetArticles, GetUser, RefreshToken
class GetArticlesUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
){
    suspend fun getArticles(
        token : String
    ) : Result<Unit> {
        return kotlin.runCatching {
            Log.d("GetArticle", "HomeViewModel.before : ${UserInfo.postLst.size}")
            userRepository.getArticles(token)
            Log.d("GetArticle", "HomeViewModel.after : ${UserInfo.postLst.size}")
            Unit
        }
    }

    suspend fun refreshToken(
        refreshToken : String
    ){
        kotlin.runCatching {
            authRepository.refresh(RefreshRequest(refreshToken))
        }
    }
}