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

        binding.joinBtn.setOnClickListener {
            checkLogin()
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    // 에러 검사
    private fun checkLogin() {
        val email : String? = binding.inputEmail.text.toString()
        val id : String? = binding.inputId.text.toString()
        val pass : String? = binding.inputPass.text.toString().trim()

        when{
            // 빈칸 확인
            (email.isNullOrBlank() || id.isNullOrBlank() || pass.isNullOrBlank()) -> showError("빈칸을 입력해주세요")

            // 이메일 중복 확인
            (email in User.emailLst) -> showError("이미 있는 이메일입니다")

            // 이메일 형식 확인
            (!emailRegex.matcher(email).matches()) -> showError("이메일 형식이 잘못되었습니다")

            // 비밀번호 최소길이 확인
            (pass.length < 8) -> showError("비밀번호의 최소길이는 8자리 이상입니다")

            // 비밀번호 형식 확인
            (!passwordRegex.matcher(pass).matches()) -> showError("비밀번호의 중간에 공백이 있습니다")

            // 아이디 중복 확인
            (id in User.idLst) -> showError("이미존재하는 아이디입니다")

            // 문제 없다면
            else -> {
                binding.wrongTxt.visibility = View.GONE
                toInputProActivity()
            }
        }
    }

    // 에러메세지 보이기
    private fun showError(msg : String): Int{
        binding.wrongTxt.visibility = View.VISIBLE
        binding.wrongTxt.text = msg
        return -1
    }

    // InputProActivity()로 이동
    private fun toInputProActivity(){
        EditUser.inputEmail = binding.inputEmail.text.toString()
        EditUser.inputId = binding.inputId.text.toString()
        EditUser.inputPass = binding.inputPass.text.toString().trim()
        val intent = Intent(this, InputProActivity::class.java)
        startActivity(intent) // 이동
    }
}