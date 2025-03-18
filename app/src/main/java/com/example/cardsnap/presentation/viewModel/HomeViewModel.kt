package com.example.cardsnap.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardsnap.data.repository.AuthRepository
import com.example.cardsnap.data.repository.UserRepository
import com.example.cardsnap.domain.entity.request.RefreshRequest
import com.example.cardsnap.data.source.user.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel(){

    private val _getArticlesSuccess = MutableSharedFlow<Boolean>()
    val getArticlesSuccess = _getArticlesSuccess.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    private val isLoading = _isLoading.asStateFlow()

    fun getArticles(
        token : String
    ) {
        if(isLoading.value) return
        viewModelScope.launch {
            kotlin.runCatching {
                _isLoading.value = true
                Log.d("GetArticle", "HomeViewModel.before : ${UserInfo.postLst.size}")
                userRepository.getArticles(token)
            }.onSuccess {
                _getArticlesSuccess.emit(true)
                _isLoading.value = false
                Log.d("GetArticle", "HomeViewModel.after : ${UserInfo.postLst.size}")
            }.onFailure { e ->
                when(e){
                    is HttpException -> {
                        if(e.code() == 401){
                            authRepository.refresh(RefreshRequest(UserInfo.refreshToken!!))
                            userRepository.getArticles(token)
                        }
                        Log.d("GetArticle", "HomeViewModel.HttpException : ${e.message}")
                    }
                    else -> {
                        Log.d("GetArticle", "HomeViewModel.Exception : ${e.message}")
                    }
                }

            }
        }
    }
}