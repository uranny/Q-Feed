package com.example.cardsnap.activity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cardsnap.R
import com.example.cardsnap.databinding.FrameJoinBinding
import com.example.cardsnap.serverDaechae.EditUser
import com.example.cardsnap.serverDaechae.User
import java.util.regex.Pattern

class FragJoin : Fragment() {

    private lateinit var binding: FrameJoinBinding

    private val emailRegex: Pattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    private val passwordRegex: Pattern = Pattern.compile("^[^\\s]+$")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrameJoinBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wrongTxt.visibility = View.GONE

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.joinBtn.setOnClickListener {
            checkLogin()
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
                toInputProFrag()
                findNavController().navigate(R.id.action_fragJoin_to_fragInput)
            }
        }
    }

    private fun showError(msg : String){
        binding.wrongTxt.visibility = View.VISIBLE
        binding.wrongTxt.setText(msg)
    }

    private fun toInputProFrag(){
        EditUser.inputEmail = binding.inputEmail.text.toString()
        EditUser.inputId = binding.inputId.text.toString()
        EditUser.inputPass = binding.inputPass.text.toString().trim()
    }


}