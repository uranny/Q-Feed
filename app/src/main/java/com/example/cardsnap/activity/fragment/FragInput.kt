package com.example.cardsnap.activity.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.compose.ui.text.LinkAnnotation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cardsnap.R
import com.example.cardsnap.data.auth.AuthRequestManager
import com.example.cardsnap.data.auth.RefreshRequest
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.data.user.UserRequestManager
import com.example.cardsnap.data.user.request.SignupRequest
import com.example.cardsnap.data.user.retrySignUp
import com.example.cardsnap.data.user.updateUserInfo
import com.example.cardsnap.databinding.FrameJoinInputBinding
import com.example.cardsnap.serverDaechae.EditUser
import com.example.cardsnap.serverDaechae.Post
import com.example.cardsnap.serverDaechae.User
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import retrofit2.http.Url
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FragInput : Fragment() {

    private lateinit var binding: FrameJoinInputBinding
    private var setURI: String? = null  // 선택된 이미지 URI를 저장할 변수
    private var setFile : File? = null
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

        if(UserInfo.myId != null){

            val nameArray = resources.getStringArray(R.array.schoolNameLst)
            val gradeArray = resources.getStringArray(R.array.schoolGradeLst)

            var index = -1

            setURI = UserInfo.imageUrl

            val gradeIndex = gradeArray.indexOf(UserInfo.grade)
            val nameIndex = nameArray.indexOf(UserInfo.affiliation)

            with(binding) {
                editName.setText(UserInfo.usernname ?: "")
                editMessage.setText(UserInfo.statusMessage ?: "")
                editTag.setText(UserInfo.hashtags.joinToString(" "){ "$it" })
                editAge.setText((UserInfo.age ?: -1).toString())
                editHeight.setText((UserInfo.height ?: -1).toString())
                editWeight.setText((UserInfo.weight ?: -1).toString())
                editHabby.setText(UserInfo.habbies ?: "")
                editLikeThing.setText(UserInfo.likes ?: "")
                editBadThing.setText(UserInfo.dislikes ?: "")
                editIdeal.setText(UserInfo.idealType ?: "")

                selectSchool.setSelection(nameIndex)
                selectGrade.setSelection(gradeIndex)
            }

            Glide.with(this)
                .load(setURI)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.img_5)
                .into(binding.editImg)
        }

        // 프로필 이미지 선택 버튼 클릭 리스너
        binding.editImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"  // 이미지 타입 선택
            }
            startActivityForResult(intent, REQUEST_IMAGE_PICK)  // 이미지 선택 액티비티 시작
        }

        // 완료 버튼 클릭 리스너
        binding.finishBtn.setOnClickListener {
            try {
                if (UserInfo.accessToken == null) {
                    findNavController().popBackStack()
                } else {
                    if(setFile != null){
                        uploadFile(setFile!!)
                    }
                    finishEdit()
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

    private fun finishEdit() {

        val signUpData = SignupRequest(
            UserInfo.uid!!,
            binding.editName.text.toString(),
            binding.selectSchool.selectedItem.toString(),
            binding.selectGrade.selectedItem.toString()[0].toString(),
            setURI!!,
            binding.editMessage.text.toString(),
            binding.editTag.text.split(" ", ","),
            binding.editAge.text.toString().toIntOrNull() ?: 0,
            binding.editHeight.text.toString().toIntOrNull() ?: 0,
            binding.editWeight.text.toString().toIntOrNull() ?: 0,
            binding.editHabby.text.toString(),
            binding.editLikeThing.text.toString(),
            binding.editBadThing.text.toString(),
            binding.editIdeal.text.toString()
        )

        lifecycleScope.launch {
            try {
                val getSUResponse = UserRequestManager.signupRequest("${UserInfo.tokenType!!} ${UserInfo.accessToken!!}", signUpData).body()

                updateUserInfo(getSUResponse)

                Toast.makeText(requireContext(), "프로필 편집 완료", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()

            } catch (e:retrofit2.HttpException){
                Log.d("signUp", "${e.message}")
                if(e.code() == 403 && UserInfo.refreshToken != null){
                    retrySignUp(
                        signUpData,
                        RefreshRequest(UserInfo.refreshToken!!)
                    )
                }
            } catch (e:Exception){
                Log.d("signUp", "${e.message}")
            }
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
            val imgUri : Uri = data.data ?: return
            try {

                Log.d("uri", setURI.toString())
                val file = uriToFile(requireContext(), imgUri)
                setFile = file
                Log.d("uri", "$file")

                Glide.with(requireContext())
                    .load(file)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.img_5)
                    .into(binding.editImg)

            } catch (e : Exception){
                Log.d("uri", e.message.toString())
                Toast.makeText(requireContext(), "이미지를 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uriToFile(context: Context, uri: Uri): File {
        val tempFile = File(context.cacheDir, "image")
        if (tempFile.exists()) {
            tempFile.delete()
        }
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
                outputStream.close()
            }
            inputStream.close()
        }
        return tempFile
    }

    private fun uploadFile(file: File) {
        lifecycleScope.launch {
            try {
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData("file", file.name, requestFile)

                Log.d("uri", "File Request: $part")

                val response = UserRequestManager.uploadProfileRequest(
                    "${UserInfo.tokenType!!} ${UserInfo.accessToken!!}",
                    part
                ).body()

                // 4. 서버 응답 처리
                if (response != null) {
                    setURI = response.message
                    Log.d("uploadFile", "Upload successful, URI: $setURI")
                } else {
                    Log.e("uploadFile", "Response is null")
                }
            } catch (e: HttpException) {
                Log.e("uploadFile", "${e.message}")
            } catch (e: Exception) {
                Log.e("uploadFile", "${e.message}")
            }
        }
    }
}