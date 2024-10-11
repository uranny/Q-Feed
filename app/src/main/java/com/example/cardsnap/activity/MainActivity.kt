package com.example.cardsnap.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.cardsnap.R
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.data.user.addPost
import com.example.cardsnap.databinding.ActivityMainBinding
import com.example.cardsnap.activity.fragment.FragChat
import com.example.cardsnap.activity.fragment.FragHome
import com.example.cardsnap.activity.fragment.FragSystem
import com.example.cardsnap.serverDaechae.User
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var backBtnTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Toast.makeText(this, "${UserInfo.accessToken}", Toast.LENGTH_SHORT).show()

        // NavHostFragment를 가져와서 NavController 설정
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.navView.setupWithNavController(navController)

        binding.navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeBtn -> {
                    navController.navigate(R.id.fragHome)
                    true
                }
                R.id.chatBtn -> {
                    navController.navigate(R.id.fragChat)
                    true
                }
                R.id.profileBtn -> {
                    navController.navigate(R.id.fragSystem)
                    true
                }
                else -> false
            }
        }
    }


    override fun onBackPressed() {
        val curTime: Long = System.currentTimeMillis()
        val gapTime: Long = curTime - backBtnTime

        if (gapTime in 0..2000) {
            super.onBackPressed()
        } else {
            backBtnTime = curTime
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
}

