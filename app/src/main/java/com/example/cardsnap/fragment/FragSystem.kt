package com.example.cardsnap.activity.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.cardsnap.R
import com.example.cardsnap.activity.ServiceActivity
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.data.user.resetUserInfo
import com.example.cardsnap.databinding.FrameSystemBinding

class FragSystem : Fragment() {

    lateinit var binding: FrameSystemBinding

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
        return binding.root
    }

    // View가 생성된 후 호출되는 함수
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // view 불러오기
        showView(view)

        if(UserInfo.id != UserInfo.myId){
            setProfileVisibility()
        }

        binding.profileEditBtn.setOnClickListener {
            activity?.findNavController(R.id.nav_host_fragment)
                ?.navigate(R.id.action_fragSystem_to_fragInput2)
        }

        // 로그아웃 버튼 클릭 시 LoginActivity로 이동하고 현재 액티비티 종료
        binding.logOutBtn.setOnClickListener {
            resetUserInfo()
            val intent = Intent(context, ServiceActivity::class.java)
            startActivity(intent)
            activity?.finish() // 현재 액티비티 종료
        }

        // 리스너 호출하여 View가 생성되었음을 알림
        listener?.onFragmentViewCreated(view)
    }

    fun setProfileVisibility() {
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

    // 프로필 편집 후 결과를 처리하는 함수
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 프로필 편집 후 결과가 성공적일 경우 View 업데이트
        if (requestCode == REQUEST_CODE_EDIT_PROFILE && resultCode == Activity.RESULT_OK) {
            view?.let { updateView(it) }
        }
    }

    // 뷰를 업데이트하는 함수
    private fun updateView(view: View) {
//        showView(view)
    }


    // view를 설정하는 함수
    private fun showView(view: View) {
        // 사용자 프로필 정보 다시 가져오기

        val nameArray = resources.getStringArray(R.array.schoolNameLst)
        val gradeArray = resources.getStringArray(R.array.schoolGradeLst)

        with(binding) {
            afilTxt.text = "${if(UserInfo.affiliation == "UNKNOWN") "대구소마고" else UserInfo.affiliation} ${UserInfo.grade ?: "1학년"}"
            nameTxt.text = UserInfo.usernname ?: "string"
            messageTxt.text = UserInfo.statusMessage ?: "string"
            tagTxt.text = UserInfo.hashtags.joinToString(" "){"#$it"}
            ageTxt.text = (UserInfo.age ?: -1).toString()
            heightTxt.text = (UserInfo.height ?: -1).toString()
            kgTxt.text = (UserInfo.weight ?: -1).toString()
            habbitTxt.text = UserInfo.habbies ?: "없음"
            likeTxt.text = UserInfo.likes ?: "없음"
            hateTxt.text = UserInfo.dislikes ?: "없음"
            idealTxt.text =UserInfo.idealType ?: "없음"
        }

        Log.d("uri", "${UserInfo.imageUrl}")

        // 이미지가 있으면 Glide를 사용해 이미지 로드, 없으면 기본 이미지 설정
        if (UserInfo.imageUrl?.isNotEmpty() == true) {
            Glide.with(this)
                .load(UserInfo.imageUrl)
                .error(R.drawable.img_5)
                .into(binding.userImg)
        } else {
            binding.userImg?.setImageResource(R.drawable.img_5)
        }
    }
}
