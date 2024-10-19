package com.Lakshan.taskfolio

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.Lakshan.taskfolio.databinding.ActivityCalenderScreenBinding
import com.Lakshan.taskfolio.databinding.ActivityHomeScreenBinding

class calender_screen : AppCompatActivity() {
    private lateinit var binding: ActivityCalenderScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCalenderScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener{
            val intent = Intent(this, add_screen::class.java)
            startActivity(intent)
        }

        binding.timerGoBtn.setOnClickListener{
            val intent = Intent(this, timing_screen::class.java)
            startActivity(intent)
        }

        binding.setingBtn.setOnClickListener{
            val intent = Intent(this, setting_screen::class.java)
            startActivity(intent)
        }

        binding.calBtn.setOnClickListener{
            val intent = Intent(this, calender_screen::class.java)
            startActivity(intent)
        }

        binding.homeBtn.setOnClickListener{
            val intent = Intent(this, Home_Screen::class.java)
            startActivity(intent)
        }
    }
}