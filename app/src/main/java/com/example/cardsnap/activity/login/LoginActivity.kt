package com.example.cardsnap.activity.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cardsnap.activity.MainActivity
import com.example.cardsnap.databinding.ActivityLoginBinding
import com.example.cardsnap.serverDaechae.User

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wrongTxt.visibility = View.GONE

        binding.loginBtn.setOnClickListener {
            loginCheck()
        }

        binding.joinBtn.setOnClickListener {
            // 회원가입 Activity로 이동
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent) // 이동
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

    private fun loginCheck(){
        val inputId = binding.inputId.text.toString()
        val inputPass = binding.inputPass.text.toString()

        val index = getUserIdex(inputId)

        if (index != -1){
            if(User.passLst[index] == inputPass){
                User.logInIndex = index

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent) // 이동
                finish()
            }
            else{
                showWrongTxt("비밀번호가 틀렸습니다")
            }
        }else{
            showWrongTxt("이메일, 아이디가 잘못 되었습니다.")
        }
    }

}