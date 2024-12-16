package com.example.cardsnap.activity.secret

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cardsnap.databinding.FrameJoinBinding
import com.example.cardsnap.serverDaechae.EditUser
import com.example.cardsnap.serverDaechae.User
import java.util.regex.Pattern

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: FrameJoinBinding

    // 검사식
    private val emailRegex: Pattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    private val passwordRegex: Pattern = Pattern.compile("^[^\\s]+$")

    // Acitvity가 만들어질떄 호출
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FrameJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wrongTxt.visibility = View.GONE


        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    // 에러메세지 보이기
    private fun showError(msg : String): Int{
        binding.wrongTxt.visibility = View.VISIBLE
        binding.wrongTxt.text = msg
        return -1
    }

}