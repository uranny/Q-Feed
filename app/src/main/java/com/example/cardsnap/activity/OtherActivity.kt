package com.example.cardsnap.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import com.example.cardsnap.fragment.FragSystem
import com.example.cardsnap.serverDaechae.Chat
import com.example.cardsnap.serverDaechae.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OtherActivity : AppCompatActivity(), FragSystem.OnFragmentInteractionListener {

    private lateinit var binding: ActivityInProfileBinding
    private val post = User.postLst[User.userProfileIndex]

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

        setProfile(view)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setProfile(view: View){

        with(view){
            findViewById<TextView>(R.id.afilTxt).text = post.userAffil
            findViewById<TextView>(R.id.nameTxt).text = post.userName
            findViewById<TextView>(R.id.messageTxt).text = post.messagetxt
            findViewById<TextView>(R.id.tagTxt).text = post.tagTxt
            findViewById<TextView>(R.id.ageTxt).text = post.age.toString() + "세"
            findViewById<TextView>(R.id.heightTxt).text = post.height.toString() + "cm"
            findViewById<TextView>(R.id.habbitTxt).text = post.habbit
            findViewById<TextView>(R.id.kgTxt).text = post.kg.toString() + "kg"
            findViewById<TextView>(R.id.likeTxt).text = post.likeTxt
            findViewById<TextView>(R.id.hateTxt).text = post.hateTxt
            findViewById<TextView>(R.id.idealTxt).text = post.idealTxt

            findViewById<LinearLayout>(R.id.linearView)?.apply {
                findViewById<Button>(R.id.profileEditBtn)?.visibility = View.GONE
                findViewById<Button>(R.id.logOutBtn)?.visibility = View.GONE

                findViewById<Button>(R.id.chatInBtn)?.apply{
                    visibility = View.VISIBLE
                    setOnClickListener {
                        checkChat()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkChat(){

        User.userChatIndex = -1

        if(User.userChatLst[User.userLogInIndex].isEmpty()){
            addChat()
        }

        for(i in User.userChatLst[User.userLogInIndex]){
            if (i.userId == post.userId){
                User.userChatIndex = User.userChatLst[User.userLogInIndex].indexOf(i)
            }
        }

        if (User.userChatIndex == -1){
            addChat()
            User.userChatIndex = 0
        }

        //InChatActivity로 이동하는 코드
        val intent = Intent(this@OtherActivity, InChatActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addChat(){
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH시 mm분") // 예상 문제 구간
        val formatted = current.format(formatter)

        User.userChatLst[User.userLogInIndex].add(0,
            Chat(
                post.userName,
                post.userImg,
                post.userAffil,
                post.userId,
                formatted
            )
        )

        User.userChatLst[User.userProfileIndex].add(0,
            Chat(
                User.postLst[User.userLogInIndex].userName,
                User.postLst[User.userLogInIndex].userImg,
                User.postLst[User.userLogInIndex].userAffil,
                User.postLst[User.userLogInIndex].userId,
                formatted
            )
        )
    }
}
