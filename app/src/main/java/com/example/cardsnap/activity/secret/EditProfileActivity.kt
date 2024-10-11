package com.example.cardsnap.activity.secret

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.cardsnap.R
import com.example.cardsnap.databinding.FrameProfileEditBinding
import com.example.cardsnap.serverDaechae.User

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: FrameProfileEditBinding

    private var setURI: String = User.postLst[User.profileIndex].userImg

    private val REQUEST_IMAGE_PICK = 1
    private val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FrameProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = User.postLst[User.profileIndex]

        val imageUri = post.userImg

        with(binding){
            editName.setText(post.userName)
            editMessage.setText(post.messagetxt)
            editTag.setText(post.tagTxt)
            editAge.setText(post.age.toString())
            editHeight.setText(post.height.toString())
            editWeight.setText(post.kg.toString())
            editHabby.setText(post.habbit)
            editLikeThing.setText(post.likeTxt)
            editBadThing.setText(post.hateTxt)
            editIdeal.setText(post.idealTxt)
        }

        setImage(imageUri)

        checkAndRequestPermissions()

        binding.editImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        binding.finishBtn.setOnClickListener {
            with(User.postLst[User.profileIndex]) {
                userName = binding.editName.text.toString()
                userImg = setURI
                userAffil =
                    "${binding.selectSchool.selectedItem} ${binding.selectGrade.selectedItem}"
                messagetxt = binding.editMessage.text.toString()
                tagTxt = binding.editTag.text.toString()
                age = binding.editAge.text.toString().toInt()
                height = binding.editHeight.text.toString().toFloat()
                kg = binding.editWeight.text.toString().toFloat()
                habbit = binding.editHabby.text.toString()
                likeTxt = binding.editLikeThing.text.toString()
                hateTxt = binding.editBadThing.text.toString()
                idealTxt = binding.editIdeal.text.toString()
            }

            setImage(imageUri)

            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 승인됨
            } else {
                // 권한이 거부됨
                Toast.makeText(this, "Permission denied to read external storage", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            setURI = (data.data).toString()
            binding.editImg.setImageURI(Uri.parse(setURI))
        }
    }

    private fun setImage(imageUri : String){

        if (imageUri.isNotEmpty()) {
            // Glide로 이미지 로드
            Glide.with(this)
                .load(Uri.parse(imageUri)) // content:// URI를 Uri 객체로 변환
                .error(R.drawable.img_4)   // 에러 시 표시할 이미지
                .into(binding.editImg)      // 이미지를 ImageView에 로드
        } else {
            // 기본 이미지 설정
            binding.editImg.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }


}
