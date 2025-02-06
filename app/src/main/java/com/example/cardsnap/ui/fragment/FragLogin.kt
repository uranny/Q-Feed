package com.example.cardsnap.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cardsnap.R
import com.example.cardsnap.activity.MainActivity
import com.example.cardsnap.adapter.item.Post
import com.example.cardsnap.data.repository.RequestManager
import com.example.cardsnap.data.request.LoginRequest
import com.example.cardsnap.data.user.User
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.databinding.FrameLoginBinding
import com.example.cardsnap.vm.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragLogin : Fragment(){

    private lateinit var binding : FrameLoginBinding
    private val authViewModel : AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.frame_login, container, false)
        binding.lifecycleOwner = this
        binding.authViewModel = authViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            authViewModel.loginRequest()
        }

        binding.joinBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragLogin_to_fragJoin)
        }

        lifecycleScope.launch {
            authViewModel.loginSuccess.collectLatest{ newState ->
                if(newState){
                    getMyPageRequest()
                }
            }
            authViewModel.errorTxt.collectLatest {
                binding.loginBtn.isEnabled = true
            }
        }
    }

    private fun getMyPageRequest(){
        lifecycleScope.launch {
            try{
                val getMPRspn = UserInfo.accessToken?.let {
                    RequestManager.myPageRequest(it).body()
                }
                Log.d("myPageRequest", "$getMPRspn")

                UserInfo.user = User(
                    id = getMPRspn?.id,
                    uid = getMPRspn?.uid,
                    username = getMPRspn?.username,
                    affiliation = getMPRspn?.affiliation,
                    grade = getMPRspn?.grade,
                    imageUrl = getMPRspn?.imageUrl,
                    statusMessage = getMPRspn?.statusMessage,
                    hashtags = getMPRspn?.hashtags ?: listOf("string", "string", "string"),
                    age = getMPRspn?.age,
                    height = getMPRspn?.height,
                    weight = getMPRspn?.weight,
                    habbies = getMPRspn?.habbies,
                    likes = getMPRspn?.likes,
                    dislikes = getMPRspn?.dislikes,
                    idealType = getMPRspn?.idealType
                )
                Log.d("login", "${UserInfo.user}")
                getArticles()
            } catch (e : retrofit2.HttpException){
                binding.loginBtn.isEnabled = true
                Log.e("mine", "${e.message}")
            } catch (e : Exception){
                binding.loginBtn.isEnabled = true
                Log.e("mine", "${e.message}")
            }
        }
    }

    private fun getArticles(){
        lifecycleScope.launch {
            try {
                val articlesRsp = UserInfo.accessToken?.let { RequestManager.articlesRequest(it) }
                Log.d("mine", "$articlesRsp")
                articlesRsp?.body()?.forEach { articles ->
                    val post =
                        UserInfo.accessToken?.let { token -> RequestManager.getUserInfoRequest(token, articles).body() }
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
                        post.habbies,
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
                binding.loginBtn.isEnabled = true
                Log.e("mine", "${e.message}")
            } catch (e : Exception){
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