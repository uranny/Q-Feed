package com.example.cardsnap.activity.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cardsnap.databinding.ActivityJoinBinding
import com.example.cardsnap.serverDaechae.User

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wrongTxt.visibility = View.GONE

        binding.joinBtn.setOnClickListener {
            if(checkLogin() == 1){
                toInputProActivity()
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun checkLogin(): Int {
        if (binding.inputEmail.text.isNullOrBlank() || binding.inputId.text.isNullOrBlank() || binding.inputPass.text.isNullOrBlank()) {
            binding.wrongTxt.visibility = View.VISIBLE
            binding.wrongTxt.text = "빈칸을 입력해주세요"
            return -1
        }

        // 이메일 중복 확인
        if (binding.inputEmail.text.toString() in User.userEmailLst) {
            binding.wrongTxt.visibility = View.VISIBLE
            binding.wrongTxt.text = "이미 존재하는 이메일 입니다"
            return -1
        }

        // 아이디 중복 확인
        if (binding.inputId.text.toString() in User.userIdLst) {
            binding.wrongTxt.visibility = View.VISIBLE
            binding.wrongTxt.text = "이미 존재하는 아이디 입니다"
            return -1
        }

        return 1
    }

    private fun toInputProActivity(){
        User.inputEmail = binding.inputEmail.text.toString()
        User.inputId = binding.inputId.text.toString()
        User.inputPass = binding.inputPass.text.toString()
        val intent = Intent(this, InputProActivity::class.java)
        startActivity(intent) // 이동
    }

}