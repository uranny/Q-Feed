package com.example.cardsnap.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardsnap.data.repository.AuthRepository
import com.example.cardsnap.data.request.LoginRequest
import com.example.cardsnap.data.request.RefreshRequest
import com.example.cardsnap.data.request.RegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _id = MutableStateFlow("string")
    val id = _id.asStateFlow()

    private val _pw = MutableStateFlow("string")
    val pw = _pw.asStateFlow()

    private val _errorTxt = MutableStateFlow("")
    val errorTxt = _errorTxt.asStateFlow()

    private val _loginSuccess = MutableSharedFlow<Boolean>()
    val loginSuccess = _loginSuccess.asSharedFlow()

    fun changeId(newId : CharSequence){
        _id.value = newId.toString()
    }

    fun changePw(newPw : CharSequence){
        _pw.value = newPw.toString()
    }

    private fun changeErrorTxt(error : String){
        _errorTxt.value = error
    }

    fun loginRequest(){
        viewModelScope.launch {
            kotlin.runCatching {
                val loginRequest = LoginRequest(_id.value, _pw.value)
                authRepository.login(loginRequest)
            }.onSuccess {
                _loginSuccess.emit(true)
                Log.d("Login", "AuthViewModel : Success")
            }.onFailure { e ->
                when(e){
                    is HttpException -> {
                        changeErrorTxt("아이디 혹은 비밀번호가 잘못되었습니다")
                        _loginSuccess.emit(false)
                        return@launch
                    }
                    else -> {

                        changeErrorTxt("알 수 없는 오류가 발생했습니다")
                        _loginSuccess.emit(false)
                        return@launch
                    }
                }
            }
        }
    }

    fun registerRequest(){
        viewModelScope.launch {
            kotlin.runCatching {
                val registerRequest = RegisterRequest(_id.value, _pw.value)
                authRepository.register(registerRequest)
            }.onFailure { e ->
                when(e){
                    is HttpException -> {
                        return@launch
                    }
                    else -> {
                        return@launch
                    }
                }
            }
        }
    }

    // 토스트 메세지 띄우기
    fun refreshRequest(refreshToken : String){
        viewModelScope.launch {
            kotlin.runCatching {
                val refreshRequest = RefreshRequest(refreshToken)
                authRepository.refresh(refreshRequest)
            }.onFailure { e ->
                when(e){
                    is HttpException -> {
                        return@launch
                    }
                    else -> {
                        return@launch
                    }
                }
            }
        }
    }
}