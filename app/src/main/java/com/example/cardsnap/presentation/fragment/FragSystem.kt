package com.example.cardsnap.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.cardsnap.R
import com.example.cardsnap.presentation.activity.ServiceActivity
import com.example.cardsnap.data.source.user.UserInfo
import com.example.cardsnap.databinding.FrameSystemBinding
import com.example.cardsnap.presentation.viewModel.SystemViewModel

class FragSystem : Fragment() {

    private lateinit var binding: FrameSystemBinding
    private val viewModel : SystemViewModel by viewModels()

    // Fragment가 그릴 View를 생성
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.frame_system, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    // View가 생성된 후 호출되는 함수
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileEditBtn.setOnClickListener {
            activity?.findNavController(R.id.nav_host_fragment)
                ?.navigate(R.id.action_fragSystem_to_fragInput2)
        }

        // 로그아웃 버튼 클릭 시 LoginActivity로 이동하고 현재 액티비티 종료
        binding.logOutBtn.setOnClickListener {
            UserInfo.resetUserInfo()
            val intent = Intent(context, ServiceActivity::class.java)
            startActivity(intent)
            activity?.finish() // 현재 액티비티 종료
        }
    }
}
