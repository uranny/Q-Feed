package com.example.cardsnap.activity.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cardsnap.R
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.databinding.FrameJoinInputBinding
import com.example.cardsnap.serverDaechae.EditUser
import com.example.cardsnap.serverDaechae.Post
import com.example.cardsnap.serverDaechae.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FragInput : Fragment() {

    private lateinit var binding: FrameJoinInputBinding
    private var setURI: String = ""  // 선택된 이미지 URI를 저장할 변수
    private val REQUEST_IMAGE_PICK = 1  // 이미지 선택 요청 코드
    private val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 2  // 외부 저장소 권한 요청 코드
    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FrameJoinInputBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkAndRequestPermissions()

        // 프로필 이미지 선택 버튼 클릭 리스너
        binding.editImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"  // 이미지 타입 선택
            }
            startActivityForResult(intent, REQUEST_IMAGE_PICK)  // 이미지 선택 액티비티 시작
        }

        // 임시 저장한 유저 프로필 데이터 불러오기
        with(binding) {
            editName.setText(EditUser.editName)
            editMessage.setText(EditUser.editMessage)
            editTag.setText(EditUser.editTag)
            editAge.setText(EditUser.editAge)
            editHeight.setText(EditUser.editHeight)
            editWeight.setText(EditUser.editWeight)
            editHabby.setText(EditUser.editHabby)
            editLikeThing.setText(EditUser.editLikeThing)
            editBadThing.setText(EditUser.editBadThing)
            editIdeal.setText(EditUser.editIdeal)
        }

        // 완료 버튼 클릭 리스너
        binding.finishBtn.setOnClickListener {
            try {
                if (UserInfo.accessToken != null) {
                    findNavController().popBackStack()
                } else {
                    findNavController().popBackStack(R.id.fragLogin, false)
                }
            } catch (e: Exception) {
                Log.e("mine", "${e.message}")
            }
        }

        // 뒤로가기 버튼 클릭 리스너
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    // 외부 저장소 읽기 권한 확인 및 요청
    private fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없으면 요청
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_PERMISSION_READ_EXTERNAL_STORAGE
            )
        }
    }

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 승인됨
            } else {
                // 권한이 거부됨
                Toast.makeText(requireActivity(), "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    // 이미지 선택 후 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            // 선택된 이미지 URI를 가져와 저장
            setURI = (data.data).toString()
            // 선택된 이미지를 이미지뷰에 설정
            binding.editImg.setImageURI(Uri.parse(setURI))
        }
    }

}