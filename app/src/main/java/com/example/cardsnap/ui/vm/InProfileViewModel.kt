package com.example.cardsnap.ui.vm

import androidx.lifecycle.ViewModel
import com.example.cardsnap.data.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InProfileViewModel(private val postUser : User) : ViewModel() {
    private val _uiState = MutableStateFlow(postUser)
    val uiState = _uiState.asStateFlow()
}