package com.example.cardsnap.activity.secret

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cardsnap.R
import com.example.cardsnap.databinding.ActivityInChatBinding
import com.example.cardsnap.serverDaechae.User

class InChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val userChat = User.chatLst[User.logInIndex][User.chatIndex]
//        val imageUri = userChat.userImg
//
//        binding.otherUserId.setText(userChat.userName)
//        binding.otherUserAfil.setText(userChat.userAffil)
//
//        if (imageUri.isNotEmpty()) {
//            // Glide로 이미지 로드
//            Glide.with(this)
//                .load(Uri.parse(imageUri)) // content:// URI를 Uri 객체로 변환
//                .error(R.drawable.img_4)   // 에러 시 표시할 이미지
//                .into(binding.otherUserImg)      // 이미지를 ImageView에 로드
//        } else {
//            // 기본 이미지 설정
//            binding.otherUserImg.setImageResource(R.drawable.ic_launcher_foreground)
//        }


        Toast.makeText(this, "구현하지 않았습니다.", Toast.LENGTH_SHORT).show()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}