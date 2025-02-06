package com.example.cardsnap.ui.fragment

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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cardsnap.R
import com.example.cardsnap.data.repository.RequestManager
import com.example.cardsnap.data.request.RefreshRequest
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.data.request.SetProfileRequest
import com.example.cardsnap.databinding.FrameJoinInputBinding
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream

class FragInput : Fragment() {

    private lateinit var binding: FrameJoinInputBinding
    private var setURI: String? = null  // 선택된 이미지 URI를 저장할 변수
    private var setFile : File? = null
    private val REQUEST_IMAGE_PICK = 1  // 이미지 선택 요청 코드
    private val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 2  // 외부 저장소 권한 요청 코드

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FrameJoinInputBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkAndRequestPermissions()

        if(UserInfo.user.id != null){

            setURI = UserInfo.user.imageUrl

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

            val setProfileData = SetProfileRequest(
                UserInfo.user.uid!!,
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

            try {
                binding.finishBtn.isEnabled = false
                if (UserInfo.accessToken == null) {
                    findNavController().popBackStack()
                } else {
                    if(setFile != null){
                        uploadFile(setFile!!, setProfileData)
                    } else{
                        finishEdit(setProfileData)
                    }
                }
            } catch (e: Exception) {
                Log.e("mine", "${e.message}")
                binding.finishBtn.isEnabled = true
            }
        }

        // 뒤로가기 버튼 클릭 리스너
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun finishEdit(setProfileRequest: SetProfileRequest) {
        lifecycleScope.launch {
            try {
                val getSUResponse =
                    UserInfo.accessToken?.let { RequestManager.setRequest(it, setProfileRequest).body() }

                UserInfo.updateUserInfo(getSUResponse)

                Toast.makeText(requireContext(), "프로필 편집 완료", Toast.LENGTH_SHORT).show()
                binding.finishBtn.isEnabled = true
                findNavController().popBackStack()

            } catch (e:HttpException){
                Log.d("signUp", "${e.message}")
                if(e.code() == 403 && UserInfo.refreshToken != null){
                    val refreshResponse = UserInfo.refreshToken?.let {
                        RequestManager.refreshRequest(RefreshRequest(it))
                    }
                    val accessToken = refreshResponse?.body()?.data?.accessToken
                    val tokenType = refreshResponse?.body()?.data?.tokenType
                    UserInfo.accessToken = "$tokenType $accessToken"
                }
            } catch (e:Exception){
                Log.d("signUp", "${e.message}")
            }
        }
    }

    private fun uploadFile(file: File, setProfileRequest: SetProfileRequest) {
        lifecycleScope.launch {
            try {
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData("image", file.name, requestFile)

                Log.d("uri", "MultipartBody.Part : $part")

                val response = UserInfo.accessToken?.let {
                    RequestManager.uploadProfileRequest(
                        it,
                        part
                    ).body()
                }

                // 4. 서버 응답 처리
                if (response != null) {
                    setURI = response.message
                    finishEdit(setProfileRequest)
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

    private fun uriToFile(context: Context, uri: Uri): File {
        val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")

        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return tempFile
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

                Log.d("uri", "기존 URI $setURI")
                Log.d("uri", "새 URI $imgUri")
                val file = uriToFile(requireContext(), imgUri)
                setFile = file
                Log.d("uri", "새 URI file로 변환 : $file")

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
}