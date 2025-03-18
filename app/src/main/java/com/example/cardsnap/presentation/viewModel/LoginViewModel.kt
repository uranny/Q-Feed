package com.example.cardsnap.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardsnap.data.repository.AuthRepository
import com.example.cardsnap.data.repository.UserRepository
import com.example.cardsnap.domain.entity.request.LoginRequest
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
class LoginViewModel @Inject constructor(
    private val authRepository : AuthRepository,
    private val userRepository : UserRepository
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
                Log.d("Login", "AuthViewModel.loginRequest Success : ${UserInfo.accessToken}")
                UserInfo.accessToken?.let { token ->
                    setMyProfile(token)
                }
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

    private fun setMyProfile(
        token : String
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                Log.d("Login", "AuthViewModel.setMyProfile : ${UserInfo.accessToken}")
                userRepository.getMyPage(token)
            }.onSuccess {
                Log.d("Login", "AuthViewModel.setMyProfile : setMyProfile Success")
                getArticles(token)
            }.onFailure { e->
                when(e){
                    is HttpException ->{
                        if(e.code() == 401){
                            refreshRequest(UserInfo.refreshToken!!)
                            getArticles(token)
                        }
                        Log.d("Login", "AuthViewModel.setMyProfile : ${e.message}")
                        changeErrorTxt("서버에 연결할 수 없습니다")
                    }
                    else -> {
                        Log.d("Login", "AuthViewModel.setMyProfile : ${e.message}")
                        changeErrorTxt("알 수 없는 오류가 발생하였습니다")
                    }
                }
            }
        }
    }


    private fun getArticles(
        token : String
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                Log.d("Login", "AuthViewModel.GetArticles.before : ${UserInfo.postLst.size}")
                userRepository.getArticles(token)
                Log.d("Login", "AuthViewModel.GetArticles.after : ${UserInfo.postLst.size}")
            }.onSuccess {
                Log.d("Login", "AuthViewModel.GetArticles.loginSuccess : ${UserInfo.postLst.size}")
                _loginSuccess.emit(true)
            }.onFailure { e->
                when(e){
                    is HttpException ->{
                        if(e.code() == 401){
                            refreshRequest(UserInfo.refreshToken!!)
                            getArticles(token)
                        }
                        Log.d("Login", "AuthViewModel.getArticles : ${e.message}")
                        changeErrorTxt("서버에 연결할 수 없습니다")
                    }
                    else -> {
                        Log.d("Login", "AuthViewModel.getArticles : ${e.message}")
                        changeErrorTxt("알 수 없는 오류가 발생하였습니다")
                    }
                }
            }
        }
    }

    // 토스트 메세지 띄우기
    private fun refreshRequest(refreshToken : String){
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