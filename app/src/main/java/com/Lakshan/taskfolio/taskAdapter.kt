package com.Lakshan.taskfolio

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class taskAdapter (private var tasks: List<task>, context: Context) : RecyclerView.Adapter<taskAdapter.TaskViewHolder>(){

    private val db: task_database = task_database(context)

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.title)
        val content: TextView = itemView.findViewById(R.id.content)
        val date: TextView = itemView.findViewById(R.id.date)
        val updatebtn : ImageButton = itemView.findViewById(R.id.edit_btn)
        val deletebtn : ImageButton = itemView.findViewById(R.id.delete_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent,false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.content.text = task.content
        holder.date.text = task.date

        holder.updatebtn.setOnClickListener{
            val intent = Intent(holder.itemView.context, update_task_screen::class.java).apply {
                putExtra("task_id",task.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deletebtn.setOnClickListener{
            db.deleteTask(task.id)
            refreshData(db.getAllTasks())
            Toast.makeText(holder.itemView.context, "Task Deleted !", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newTasks: List<task>){
        tasks = newTasks
        notifyDataSetChanged()
    }
}