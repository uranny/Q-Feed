package com.example.cardsnap.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.cardsnap.R
import com.example.cardsnap.databinding.ActivityMainBinding
import com.example.cardsnap.fragment.FragChat
import com.example.cardsnap.fragment.FragHome
import com.example.cardsnap.fragment.FragSystem
import com.example.cardsnap.serverDaechae.User
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.linearLayout)
    }
    private var backBtnTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeBtn -> {
                    setFrag(0)
                    true
                }
                R.id.chatBtn -> {
                    setFrag(1)
                    true
                }
                R.id.profileBtn -> {
                    for (i in User.postLst) {
                        if (i.userId == User.userIdLst[User.userLogInIndex]) {
                            User.userProfileIndex = User.postLst.indexOf(i)
                        }
                    }
                    setFrag(2)
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            setFrag(0)
        }
    }

    private fun setFrag(fragNum: Int) {
        val ft = supportFragmentManager.beginTransaction()
        when (fragNum) {
            0 -> ft.replace(R.id.Frame, FragHome()).commit()
            1 -> ft.replace(R.id.Frame, FragChat()).commit()
            2 -> {
                val fragSystem = FragSystem()
                ft.replace(R.id.Frame, fragSystem).commit()
            }
        }
    }

    override fun onBackPressed() {

        val curTime : Long = System.currentTimeMillis()
        val gapTime : Long = curTime - backBtnTime

        if(gapTime in 0..2000){
            super.onBackPressed()
        }else{
            backBtnTime = curTime
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }

    }
}
