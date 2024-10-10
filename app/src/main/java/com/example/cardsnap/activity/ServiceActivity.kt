package com.example.cardsnap.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cardsnap.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}