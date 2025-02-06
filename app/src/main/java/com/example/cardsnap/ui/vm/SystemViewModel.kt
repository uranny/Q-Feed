package com.example.cardsnap.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardsnap.data.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SystemViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserInfo.user)
    val uiState = _uiState.asStateFlow()

    fun updateUiState(
        id: Int?,
        uid: String?,
        username: String?,
        affiliation: String?,
        grade: String?,
        imageUrl: String? ,
        statusMessage: String? ,
        hashtags: List<String>? ,
        age: Int? ,
        height: Int? ,
        weight: Int?,
        habbies: String?,
        likes: String? ,
        dislikes: String? ,
        idealType: String?
    ){
        _uiState.update { currentState ->
            currentState.copy(
                id = id,
                uid = uid,
                username = username,
                affiliation = affiliation,
                grade = grade,
                imageUrl = imageUrl,
                statusMessage = statusMessage,
                hashtags = hashtags,
                age = age,
                height = height,
                weight = weight,
                habbies = habbies,
                likes = likes,
                dislikes = dislikes,
                idealType = idealType
            )
        }
    }
}