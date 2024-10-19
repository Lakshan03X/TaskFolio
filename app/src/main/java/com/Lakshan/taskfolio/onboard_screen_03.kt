package com.Lakshan.taskfolio

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.Lakshan.taskfolio.databinding.ActivityMainBinding
import com.Lakshan.taskfolio.databinding.ActivityOnboardScreen03Binding

class onboard_screen_03 : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardScreen03Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardScreen03Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            val intent = Intent(this, Home_Screen::class.java)
            startActivity(intent)
        }
    }
}