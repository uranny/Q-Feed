package com.example.cardsnap.activity.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cardsnap.databinding.ActivityLoginEditBinding
import com.example.cardsnap.serverDaechae.Post
import com.example.cardsnap.serverDaechae.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InputProActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginEditBinding
    private var setURI: String = ""  // 선택된 이미지 URI를 저장할 변수

    private val REQUEST_IMAGE_PICK = 1  // 이미지 선택 요청 코드
    private val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 2  // 외부 저장소 권한 요청 코드

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 외부 저장소 읽기 권한 요청
        checkAndRequestPermissions()

        // 프로필 이미지 선택 버튼 클릭 리스너
        binding.editImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"  // 이미지 타입 선택
            }
            startActivityForResult(intent, REQUEST_IMAGE_PICK)  // 이미지 선택 액티비티 시작
        }

        // 저장한 유저 프로필 데이터 불러오기
        with(binding){
            editName.setText(User.editName)
            editMessage.setText(User.editMessage)
            editTag.setText(User.editTag)
            editAge.setText(User.editAge)
            editHeight.setText(User.editHeight)
            editWeight.setText(User.editWeight)
            editHabby.setText(User.editHabby)
            editLikeThing.setText(User.editLikeThing)
            editBadThing.setText(User.editBadThing)
            editIdeal.setText(User.editIdeal)
        }

        // 완료 버튼 클릭 리스너
        binding.finishBtn.setOnClickListener {
            // 필수 필드가 모두 채워져 있는지 확인
            if (!binding.editName.text.isNullOrBlank() &&
                !binding.editMessage.text.isNullOrBlank() &&
                !binding.editTag.text.isNullOrBlank() &&
                !binding.editAge.text.isNullOrBlank() &&
                setURI.isNotEmpty()  // 이미지가 선택되었는지 확인
            ) {
                // 현재 날짜와 시간을 가져와 포맷팅
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formatted = current.format(formatter)

                // 해시태그 만들기
                val wordLst: List<String> = binding.editTag.text.toString().split(" ")
                val result = wordLst.joinToString(" ") { "#$it" }

                // 유저 정보를 리스트에 추가
                User.userEmailLst.add(0, User.inputEmail)
                User.userIdLst.add(0, User.inputId)
                User.userPassLst.add(0, User.inputPass)

                // 새로운 포스트 객체를 생성하고 유저 포스트 리스트에 추가
                User.postLst.add(0, Post(
                    binding.editName.text.toString(),
                    "${binding.selectSchool.selectedItem} ${binding.selectGrade.selectedItem}",
                    formatted,
                    arrayListOf(),
                    setURI,  // 선택된 이미지 URI
                    binding.editMessage.text.toString(),
                    result,
                    binding.editAge.text.toString().toInt(),
                    binding.editHeight.text.toString().toFloat(),
                    binding.editWeight.text.toString().toFloat(),
                    binding.editHabby.text.toString(),
                    binding.editLikeThing.text.toString(),
                    binding.editBadThing.text.toString(),
                    binding.editIdeal.text.toString(),
                    User.inputId
                ))

                // 유저 채팅 리스트 생성
                User.userChatLst.add(0, arrayListOf())

                Toast.makeText(this, "회원가입을 성공하였습니다.", Toast.LENGTH_SHORT).show()

                // 액티비티 종료
                finish()
            } else {
                // 필수 항목이 채워지지 않았을 경우 경고 메시지 표시
                binding.wrongTxt.visibility = View.VISIBLE
            }
        }

        // 뒤로가기 버튼 클릭 리스너
        binding.backBtn.setOnClickListener {
            // 입력된 내용을 저장
            with(User){
                editName = binding.editName.text.toString()
                editAffil = "${binding.selectSchool.selectedItem} ${binding.selectGrade.selectedItem}"
                editMessage = binding.editMessage.text.toString()
                editTag = binding.editTag.text.toString()
                editAge = binding.editAge.text.toString()
                editHeight = binding.editHeight.text.toString()
                editWeight = binding.editWeight.text.toString()
                editHabby = binding.editHabby.text.toString()
                editLikeThing = binding.editLikeThing.text.toString()
                editBadThing = binding.editBadThing.text.toString()
                editIdeal = binding.editIdeal.text.toString()
            }

            // 액티비티 종료
            finish()
        }
    }

    // 외부 저장소 읽기 권한 확인 및 요청
    private fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없으면 요청
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)
        }
    }

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 승인됨
            } else {
                // 권한이 거부됨
                Toast.makeText(this, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
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
