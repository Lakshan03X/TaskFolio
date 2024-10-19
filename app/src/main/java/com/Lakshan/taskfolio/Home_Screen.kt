package com.Lakshan.taskfolio

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.Lakshan.taskfolio.databinding.ActivityAddScreenBinding
import com.Lakshan.taskfolio.databinding.ActivityHomeScreenBinding
import com.Lakshan.taskfolio.databinding.ActivityMainBinding

class Home_Screen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var db: task_database
    private lateinit var taskAdapter: taskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = task_database(this)
        taskAdapter = taskAdapter(db.getAllTasks(),this)

        binding.taskRecyle.layoutManager = LinearLayoutManager(this)
        binding.taskRecyle.adapter = taskAdapter

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

    override fun onResume() {
        super.onResume()
        taskAdapter.refreshData(db.getAllTasks())
    }
}