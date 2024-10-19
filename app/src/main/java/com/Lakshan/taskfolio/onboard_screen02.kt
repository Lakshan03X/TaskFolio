package com.Lakshan.taskfolio

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.Lakshan.taskfolio.databinding.ActivityMainBinding
import com.Lakshan.taskfolio.databinding.ActivityOnboardScreen02Binding

class onboard_screen02 : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardScreen02Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardScreen02Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextBtn.setOnClickListener {
            val intent = Intent(this, onboard_screen_03::class.java)
            startActivity(intent)
        }

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        }
    }