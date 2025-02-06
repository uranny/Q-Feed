package com.example.cardsnap.ui.vm

import androidx.lifecycle.ViewModel
import com.example.cardsnap.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val repository : UserRepository
) : ViewModel()