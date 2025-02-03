package com.example.cardsnap.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.cardsnap.R
import com.example.cardsnap.activity.ServiceActivity
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.databinding.FrameSystemBinding
import com.example.cardsnap.vm.SystemViewModel

class FragSystem : Fragment() {

    private lateinit var binding: FrameSystemBinding
    private val viewModel : SystemViewModel by viewModels()

    // 프로필 수정 액티비티에 사용될 요청 코드를 상수로 정의
    companion object {
        private const val REQUEST_CODE_EDIT_PROFILE = 1
    }

    // Fragment가 생성된 후 인터페이스를 통해 View가 준비되었을 때 상호작용을 처리
    interface OnFragmentInteractionListener {
        fun onFragmentViewCreated(view: View)
    }

    // 상호작용 리스너
    private var listener: OnFragmentInteractionListener? = null

    // Fragment가 Context에 연결될 때 호출되며, 상호작용 리스너 설정
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnFragmentInteractionListener
    }

    // Fragment가 그릴 View를 생성
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrameSystemBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    // View가 생성된 후 호출되는 함수
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(viewModel.uiState.value.id != UserInfo.myId){
            setProfileVisibility()
        }

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

        // 리스너 호출하여 View가 생성되었음을 알림
        listener?.onFragmentViewCreated(view)
    }

    private fun setProfileVisibility() {
        with(binding) {
            profileEditBtn.visibility = View.GONE
            logOutBtn.visibility = View.GONE
            chatInBtn.visibility = View.VISIBLE
            chatInBtn.setOnClickListener {
                activity?.findNavController(R.id.nav_host_fragment)
                    ?.navigate(R.id.action_fragOther_to_fragInChat)
            }
        }
    }
}
