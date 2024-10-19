package com.Lakshan.taskfolio

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.Lakshan.taskfolio.databinding.ActivityAddScreenBinding
import com.Lakshan.taskfolio.databinding.ActivityMainBinding

class add_screen : AppCompatActivity() {

    private lateinit var binding: ActivityAddScreenBinding
    private lateinit var db: task_database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = task_database(this)

        binding.saveBtn.setOnClickListener {
            val title = binding.title.text.toString()
            val content = binding.content.text.toString()
            val date = binding.date.text.toString()
            val task = task(0, title, content, date)
            db.insertTask(task)
            finish()
            Toast.makeText(this, "Task Added ! ", Toast.LENGTH_SHORT).show()
        }
    }
}