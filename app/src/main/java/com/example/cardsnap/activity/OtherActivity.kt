package com.example.cardsnap.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cardsnap.R
import com.example.cardsnap.databinding.ActivityInProfileBinding
import com.example.cardsnap.databinding.FrameSystemBinding
import com.example.cardsnap.activity.fragment.FragSystem
import com.example.cardsnap.serverDaechae.Chat
import com.example.cardsnap.serverDaechae.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.typeOf

class OtherActivity : AppCompatActivity(), FragSystem.OnFragmentInteractionListener {

    lateinit var binding: ActivityInProfileBinding
    lateinit var fragBinding : FrameSystemBinding
    private val post = User.postLst[User.profileIndex]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }

        setFrag()
    }

    private fun setFrag() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.otherProfile, FragSystem()).commit()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onFragmentViewCreated(view: View) {
        fragBinding = FrameSystemBinding.bind(view) // 여기서 초기화
        setProfile(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setProfile(view: View){
        with(fragBinding){
            afilTxt.text = post.userAffil
            nameTxt.text = post.userName
            messageTxt.text = post.messagetxt
            tagTxt.text = post.tagTxt
            ageTxt.text = post.age.toString() + "세"
            heightTxt.text = post.height.toString() + "cm"
            habbitTxt.text = post.habbit
            kgTxt.text = post.kg.toString() + "kg"
            likeTxt.text = post.likeTxt
            hateTxt.text = post.hateTxt
            idealTxt.text = post.idealTxt
            profileEditBtn.visibility = View.GONE
            logOutBtn.visibility = View.GONE
            chatInBtn.visibility = View.VISIBLE

            chatInBtn.setOnClickListener {
                checkChat()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkChat(){

        try {
            User.chatIndex = -1

            if(User.chatLst[User.logInIndex].isEmpty()){
                addChat()
            }

            for(i in User.chatLst[User.logInIndex]){
                if (i.userId == post.userId){
                    User.chatIndex = User.chatLst[User.logInIndex].indexOf(i)
                }
            }

            if (User.chatIndex == -1){
                addChat()
                User.chatIndex = 0
            }
        }catch (e : Exception){
            Log.e("MyTag", "${e.message}")
        }

        //InChatActivity로 이동하는 코드
        val intent = Intent(this@OtherActivity, InChatActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addChat(){
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH시 mm분") // 예상 문제 구간
        val formatted = current.format(formatter) // String

        User.chatLst[User.logInIndex].add(0,
            Chat(
                post.userName,
                post.userImg,
                post.userAffil,
                post.userId,
                formatted
            )
        )

        User.chatLst[User.profileIndex].add(0,
            Chat(
                User.postLst[User.logInIndex].userName,
                User.postLst[User.logInIndex].userImg,
                User.postLst[User.logInIndex].userAffil,
                User.postLst[User.logInIndex].userId,
                formatted
            )
        )
    }
}