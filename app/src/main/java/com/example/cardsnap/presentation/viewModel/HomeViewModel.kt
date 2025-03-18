package com.example.cardsnap.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardsnap.data.source.user.UserInfo
import com.example.cardsnap.domain.usecase.GetArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArticlesUseCase : GetArticlesUseCase
) : ViewModel(){

    private val _getArticlesSuccess = MutableSharedFlow<Boolean>()
    val getArticlesSuccess = _getArticlesSuccess.asSharedFlow()

    private val _apiMsg = MutableStateFlow("")
    val apiMsg = _apiMsg.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    private val isLoading = _isLoading.asStateFlow()

    fun getArticles(
        token : String
    ) {
        if(isLoading.value) return
        viewModelScope.launch {
            _isLoading.value = true
            kotlin.runCatching {
                Log.d("GetArticle", "HomeViewModel.before : ${UserInfo.postLst.size}")
                getArticlesUseCase.getArticles(token)
            }.onSuccess {
                _getArticlesSuccess.emit(true)
                _isLoading.value = false
                Log.d("GetArticle", "HomeViewModel.after : ${UserInfo.postLst.size}")
            }.onFailure { e ->
                when(e){
                    is HttpException -> {
                        if(e.code() == 401){
                            getArticlesUseCase.refreshToken(UserInfo.refreshToken!!)
                            getArticlesUseCase.getArticles(UserInfo.accessToken!!)
                        }
                        _apiMsg.value = "서버와 연결이 되지 않습니다"
                    }
                    is TimeoutException -> {
                        _apiMsg.value = "WIFI 연결을 확인해주세요"
                    }
                    else -> {
                        _apiMsg.value = "알 수 없는 오류가 발생하였습니다"
                    }
                }
            }
        }
    }
}