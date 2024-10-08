package com.example.cardsnap.activity.login

import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.transition.Visibility
import com.example.cardsnap.R
import com.example.cardsnap.activity.MainActivity
import com.example.cardsnap.data.auth.AuthRequestManager
import com.example.cardsnap.data.auth.LoginRequest
import com.example.cardsnap.databinding.ActivityLoginBinding
import com.example.cardsnap.serverDaechae.User
import kotlinx.coroutines.launch

class FragLogin() : Fragment(){

    private lateinit var binding : ActivityLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wrongTxt.visibility = View.GONE

        binding.loginBtn.setOnClickListener {
            loginRequest()
        }

        binding.joinBtn.setOnClickListener {
        }
    }

    private fun showWrongTxt(text : String){
        binding.wrongTxt.visibility = View.VISIBLE
        binding.wrongTxt.setText(text)
    }

    private fun getUserIdex(inputId : String) : Int{
        return when{
            inputId in User.idLst -> User.idLst.indexOf(inputId)
            inputId in User.emailLst -> User.emailLst.indexOf(inputId)
            else -> -1
        }
    }

    private fun loginRequest(){
        if (binding.inputId.text.isEmpty() || binding.inputPass.text.isEmpty()){
            showWrongTxt("빈칸을 입력해주세요")
            return
        }

        val id = binding.inputId.text.toString()
        val pw = binding.inputPass.text.toString()

        lifecycleScope.launch {
            try {
                val loginRequest = LoginRequest(id, pw)
                val response = AuthRequestManager.loginRequest(loginRequest)
                Log.d(TAG, "resoponse.header : ${response.code()}")
                Log.d(TAG, "LoginFragment loginbutton click5")

                val accessToken = response.body()?.accessToken
                val refreshToken = response.body()?.refreshToken
                Log.d("mine", "accesstoken is $accessToken")
                Log.d("mine", "refreshtoken is $refreshToken")

                getUserIdex(id)

                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)

            } catch (e: retrofit2.HttpException){
                showWrongTxt("아이디, 비번이 잘못되었스빈다")
                Log.e("mine", "${e.message}")
            } catch (e: Exception){
                showWrongTxt("나는 아무거또 멀리요")
                Log.e("mine", "${e.message}")
            }
        }

    }

}