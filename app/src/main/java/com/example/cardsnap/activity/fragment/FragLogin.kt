package com.example.cardsnap.activity.fragment

import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cardsnap.R
import com.example.cardsnap.activity.MainActivity
import com.example.cardsnap.data.auth.AuthRequestManager
import com.example.cardsnap.data.auth.LoginRequest
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.data.user.addPost
import com.example.cardsnap.databinding.FrameLoginBinding
import kotlinx.coroutines.launch

class FragLogin() : Fragment(){

    private lateinit var binding : FrameLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrameLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wrongTxt.visibility = View.GONE

        binding.loginBtn.setOnClickListener {
//            loginRequest()

            goMain()

        }

        binding.joinBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragLogin_to_fragJoin)
        }
    }

    private fun showWrongTxt(text : String){
        binding.wrongTxt.visibility = View.VISIBLE
        binding.wrongTxt.setText(text)
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

                goMain()

            } catch (e: retrofit2.HttpException){
                showWrongTxt("아이디, 비번이 잘못되었스빈다")
                Log.e("mine", "${e.message}")
            } catch (e: Exception){
                showWrongTxt("나는 아무거또 멀리요")
                Log.e("mine", "${e.message}")
            }
        }

    }

    private fun goMain(){
        UserInfo.postLst = arrayListOf()
        UserInfo.postPosition = 1
        addPost()

        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

}