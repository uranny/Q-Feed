package com.example.cardsnap.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cardsnap.R
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.databinding.ActivityMainBinding
import com.example.cardsnap.fragment.FragChat
import com.example.cardsnap.fragment.FragHome
import com.example.cardsnap.fragment.FragSystem

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var backBtnTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Toast.makeText(this, "${UserInfo.accessToken}", Toast.LENGTH_SHORT).show()

        // NavHostFragment를 가져와서 NavController 설정
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.navView.setupWithNavController(navController)

        binding.navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeBtn -> {
                    // Home은 BackStack에 쌓이지 않게 설정
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.fragHome, false)
                        .build()
                    navController.navigate(R.id.fragHome, null, navOptions)
                    true
                }
                R.id.chatBtn -> {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.fragChat, false)
                        .build()
                    navController.navigate(R.id.fragChat, null, navOptions)
                    true
                }
                R.id.profileBtn -> {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.fragSystem, false)
                        .build()
                    navController.navigate(R.id.fragSystem, null, navOptions)
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val currentNav = navHostFragment.childFragmentManager.fragments[0]

        if (currentNav is FragChat || currentNav is FragHome || currentNav is FragSystem) {
            // BackStack이 비어 있을 경우 종료 로직 실행
            val curTime: Long = System.currentTimeMillis()
            val gapTime: Long = curTime - backBtnTime

            if (gapTime in 0..2000) {
                super.onBackPressed()  // 앱 종료
            } else {
                backBtnTime = curTime
                Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
        } else{
            navHostFragment.navController.popBackStack()
        }
    }
}

