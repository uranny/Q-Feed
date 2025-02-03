package com.example.cardsnap.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cardsnap.R
import com.example.cardsnap.data.auth.RequestManager
import com.example.cardsnap.data.request.RegisterRequest
import com.example.cardsnap.data.response.RegisterResponse
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.databinding.FrameJoinBinding
import kotlinx.coroutines.launch
import retrofit2.Response
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
        val id : String? = binding.inputId.text.toString()
        val pass : String? = binding.inputPass.text.toString().trim()

        when{

            (UserInfo.accessToken != null) -> {
                findNavController().navigate(R.id.action_fragJoin_to_fragInput)
            }
            // 빈칸 확인
            (id.isNullOrBlank() || pass.isNullOrBlank()) -> showError("빈칸을 입력해주세요")

            // 비밀번호 최소길이 확인
            (pass.length < 8) -> showError("비밀번호의 최소길이는 8자리 이상입니다")

            // 비밀번호 형식 확인
            (!passwordRegex.matcher(pass).matches()) -> showError("비밀번호의 중간에 공백이 있습니다")

            // 문제 없다면
            else -> {
                binding.wrongTxt.visibility = View.GONE
                registerRequest(id, pass)
            }
        }
    }

    private fun showError(msg : String){
        binding.wrongTxt.visibility = View.VISIBLE
        binding.wrongTxt.setText(msg)
    }

    private fun registerRequest(id : String, pw : String){
        lifecycleScope.launch {
            var response : Response<RegisterResponse>? = null
            try {
                val registerRequest = RegisterRequest(
                    id,
                    pw
                )
                response = RequestManager.registerRequest(registerRequest)
                Log.d("join", "resoponse.header : ${response.body()}")

                Toast.makeText(requireContext(), "회원가입 성공", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_fragJoin_to_fragInput)

            } catch (e: retrofit2.HttpException){
                showError("${response?.body()?.message}")
                Log.e("join", "${e.message}")
            } catch (e: Exception){
                showError("나는 아무거또 멀라요")
                Log.e("join", "${e.message}")
            }
        }
    }
}