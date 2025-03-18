package com.example.cardsnap.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cardsnap.R
import com.example.cardsnap.presentation.activity.MainActivity
import com.example.cardsnap.databinding.FrameLoginBinding
import com.example.cardsnap.presentation.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragLogin : Fragment(){

    private lateinit var binding : FrameLoginBinding
    private val loginViewModel : LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.frame_login, container, false)
        binding.lifecycleOwner = this
        binding.loginViewModel = loginViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            loginViewModel.loginRequest()
        }

        binding.joinBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragLogin_to_fragJoin)
        }

        lifecycleScope.launch {
            loginViewModel.loginSuccess.collectLatest{ newState ->
                if(newState){
                    goMain()
                }
            }
            loginViewModel.errorTxt.collectLatest {
                binding.loginBtn.isEnabled = true
            }
        }
    }

    private fun goMain(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}