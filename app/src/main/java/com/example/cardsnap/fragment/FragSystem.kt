package com.example.cardsnap.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.cardsnap.R
import com.example.cardsnap.activity.EditProfileActivity
import com.example.cardsnap.activity.login.LoginActivity
import com.example.cardsnap.serverDaechae.User

class FragSystem : Fragment() {

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
        return inflater.inflate(R.layout.frame_system, container, false)
    }

    // View가 생성된 후 호출되는 함수
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 로그아웃 버튼, 프로필 편집 버튼, 이미지 뷰 초기화
        val logoutBtn = view.findViewById<Button>(R.id.logOutBtn)
        val editBtn = view.findViewById<Button>(R.id.profileEditBtn)

        // view 불러오기
        showView(view)

        // 프로필 편집 버튼 클릭 시 EditProfileActivity로 이동
        editBtn.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_EDIT_PROFILE)
        }

        // 로그아웃 버튼 클릭 시 LoginActivity로 이동하고 현재 액티비티 종료
        logoutBtn.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish() // 현재 액티비티 종료
        }

        // 리스너 호출하여 View가 생성되었음을 알림
        listener?.onFragmentViewCreated(view)
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
        showView(view)
    }


    // view를 설정하는 함수
    private fun showView(view : View){
        // 사용자 프로필 정보 다시 가져오기
        val userProfile = User.postLst[User.userProfileIndex]

        // 텍스트 뷰 업데이트
        view.findViewById<TextView>(R.id.afilTxt)?.text = userProfile.userAffil
        view.findViewById<TextView>(R.id.nameTxt)?.text = userProfile.userName
        view.findViewById<TextView>(R.id.messageTxt)?.text = userProfile.messagetxt
        view.findViewById<TextView>(R.id.tagTxt)?.text = userProfile.tagTxt
        view.findViewById<TextView>(R.id.ageTxt)?.text = "${userProfile.age}세"
        view.findViewById<TextView>(R.id.heightTxt)?.text = "${userProfile.height}cm"
        view.findViewById<TextView>(R.id.habbitTxt)?.text = userProfile.habbit
        view.findViewById<TextView>(R.id.kgTxt)?.text = "${userProfile.kg}kg"
        view.findViewById<TextView>(R.id.likeTxt)?.text = userProfile.likeTxt
        view.findViewById<TextView>(R.id.hateTxt)?.text = userProfile.hateTxt
        view.findViewById<TextView>(R.id.idealTxt)?.text = userProfile.idealTxt

        // 이미지 URI 가져오기
        val imageUri = userProfile.userImg

        // 이미지가 있으면 Glide를 사용해 이미지 로드, 없으면 기본 이미지 설정
        if (imageUri.isNotEmpty()) {
            Glide.with(this)
                .load(Uri.parse(imageUri))
                .error(R.drawable.img_4)
                .into(view.findViewById(R.id.userImg))
        } else {
            view.findViewById<ImageView>(R.id.userImg)
                ?.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }
}
