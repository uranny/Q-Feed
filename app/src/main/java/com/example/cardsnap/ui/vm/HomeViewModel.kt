package com.example.cardsnap.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardsnap.data.request.SetProfileRequest
import com.example.cardsnap.data.usecase.GetUserUseCase
import com.example.cardsnap.data.user.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase : GetUserUseCase
) : ViewModel(){

    private val _getArticlesSuccess = MutableSharedFlow<Boolean>()
    val getArticlesSuccess = _getArticlesSuccess.asSharedFlow()

    private val _oldSize = MutableStateFlow(0)
    val oldSize = _oldSize.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    private val isLoading = _isLoading.asStateFlow()

    fun sendUserData(
        token : String,
        image : MultipartBody.Part,
        setProfileRequest: SetProfileRequest
    ) {
        viewModelScope.launch {
            useCase.uploadImageAndSendUserData(token, image, setProfileRequest)
        }
    }

    fun getArticles(
        token : String
    ) {
        if(isLoading.value) return
        viewModelScope.launch {
            _isLoading.value = true
            _oldSize.value = UserInfo.postLst.size
            Log.d("GetArticle", "HomeViewModel.before : ${UserInfo.postLst.size}")
            useCase.getArticles(token)
            _getArticlesSuccess.emit(true)

            _isLoading.value = false
            Log.d("GetArticle", "HomeViewModel.after : ${UserInfo.postLst.size}")
        }
    }
}