package com.example.cardsnap.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.cardsnap.data.repository.AuthRepository
import com.example.cardsnap.data.repository.UserRepository
import com.example.cardsnap.data.source.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class InProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserInfo.user)
    val uiState = _uiState.asStateFlow()
}