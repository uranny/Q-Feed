package com.example.cardsnap.activity.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cardsnap.R
import com.example.cardsnap.activity.MainActivity
import com.example.cardsnap.adapter.adapter_class.Post
import com.example.cardsnap.data.auth.RequestManager
import com.example.cardsnap.data.request.LoginRequest
import com.example.cardsnap.data.user.UserInfo
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
            binding.loginBtn.isEnabled = false
            loginRequest()
        }

        binding.joinBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragLogin_to_fragJoin)
        }
    }

    private fun showTxt(text : String){
        binding.wrongTxt.visibility = View.VISIBLE
        binding.wrongTxt.setText(text)
    }

    private fun loginRequest(){
        if (binding.inputId.text.isEmpty() || binding.inputPass.text.isEmpty()){
            showTxt("빈칸을 입력해주세요")
            binding.loginBtn.isEnabled = true
            return
        }

        val id = binding.inputId.text.toString()
        val pw = binding.inputPass.text.toString()

        lifecycleScope.launch {
            try {
                val loginRequest = LoginRequest(id, pw)
                val getLoginResponse = RequestManager.loginRequest(loginRequest)
                Log.d("loginRequest", "${getLoginResponse.body()}")

                UserInfo.accessToken = getLoginResponse.body()?.data?.accessToken
                UserInfo.refreshToken = getLoginResponse.body()?.data?.refreshToken
                UserInfo.tokenType = getLoginResponse.body()?.data?.tokenType

                if(UserInfo.accessToken != null){
                    getMyPageRequest()
                }else{
                    showTxt("토큰을 받아오지 못하였습니다")
                }
            } catch (e: retrofit2.HttpException){
                showTxt("아이디, 비번이 잘못되었습니다")
                Log.e("mine", "${e.message}")
            } catch (e: Exception){
                showTxt("알 수 없는 오류가 발생하였습니다")
                Log.e("mine", "${e.message}")
            }
        }
    }

    private fun getMyPageRequest(){
        lifecycleScope.launch {
            try{
                val getMPRspn = RequestManager.myPageRequest("${UserInfo.tokenType!!} ${UserInfo.accessToken!!}").body()
                Log.d("myPageRequest", "${getMPRspn}")

                val arrayMap = mapOf(
                    "FIRST_GRADE" to "1학년",
                    "SECOND_GRADE" to "2학년",
                    "THIRD_GRADE" to "3학년"
                )

                with(UserInfo){
                    myId = getMPRspn?.id
                    id = getMPRspn?.id
                    uid = getMPRspn?.uid
                    usernname = getMPRspn?.username
                    affiliation = getMPRspn?.affiliation
                    grade = arrayMap[getMPRspn?.grade] ?: "0학년"
                    imageUrl = getMPRspn?.imageUrl
                    statusMessage = getMPRspn?.statusMessage
                    hashtags = getMPRspn?.hashtags ?: listOf("string", "string", "string")
                    age = getMPRspn?.age
                    height = getMPRspn?.height
                    weight = getMPRspn?.weight
                    habbies = getMPRspn?.hobbies
                    likes = getMPRspn?.likes
                    dislikes = getMPRspn?.dislikes
                    idealType = getMPRspn?.idealType

                    getArticles()
                }
            } catch (e : retrofit2.HttpException){
                showTxt("마이페이지를 받아오지 못 하였습니다")
                binding.loginBtn.isEnabled = true
                Log.e("mine", "${e.message}")
            } catch (e : Exception){
                showTxt("알 수 없는 오류가 발생하였습니다")
                binding.loginBtn.isEnabled = true
                Log.e("mine", "${e.message}")
            }
        }
    }

    private fun getArticles(){
        lifecycleScope.launch {
            try {
                val articlesRsp = RequestManager.articlesRequest("${UserInfo.tokenType!!} ${UserInfo.accessToken!!}")
                Log.d("mine", "${articlesRsp}")
                articlesRsp.body()?.forEach {
                    val post = RequestManager.getUserInfoRequest("${UserInfo.tokenType!!} ${UserInfo.accessToken!!}", it).body()
                    val response = Post(
                        post!!.id,
                        post.uid,
                        post.username,
                        post.affiliation,
                        post.grade,
                        post.imageUrl,
                        post.statusMessage,
                        post.hashtags,
                        post.age,
                        post.height,
                        post.weight,
                        post.hobbies,
                        post.likes,
                        post.dislikes,
                        post.idealType,
                        arrayListOf(),
                        arrayListOf()
                    )
                    UserInfo.postLst.add(response)
                }

                binding.loginBtn.isEnabled = true
                goMain()
            } catch (e : retrofit2.HttpException){
                showTxt("마이페이지를 받아오지 못 하였습니다")
                binding.loginBtn.isEnabled = true
                Log.e("mine", "${e.message}")
            } catch (e : Exception){
                showTxt("알 수 없는 오류가 발생하였습니다")
                binding.loginBtn.isEnabled = true
                Log.e("mine", "${e.message}")
            }
        }
    }

    private fun goMain(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}