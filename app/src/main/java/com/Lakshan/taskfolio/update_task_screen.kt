package com.Lakshan.taskfolio

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.Lakshan.taskfolio.databinding.ActivityOnboardScreen02Binding
import com.Lakshan.taskfolio.databinding.ActivityUpdateScreenBinding

class update_task_screen : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateScreenBinding
    private lateinit var db:task_database
    private var taskID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = task_database(this)

        taskID = intent.getIntExtra("task_id",-1)
        if (taskID == -1){
            finish()
            return
        }

        val task = db.getTaskById(taskID)
        binding.updateTitle.setText(task.title)
        binding.updateContent.setText(task.content)
        binding.updateDate.setText(task.date)

        binding.updateSaveBtn.setOnClickListener {
            val newTitle = binding.updateTitle.text.toString()
            val newContent = binding.updateContent.text.toString()
            val newDate = binding.updateDate.text.toString()
            val updateTask = task(taskID, newTitle, newContent, newDate)
            db.updateTask(updateTask)
            finish()
            Toast.makeText(this, "Task Updated !", Toast.LENGTH_SHORT).show()
        }
    }
}