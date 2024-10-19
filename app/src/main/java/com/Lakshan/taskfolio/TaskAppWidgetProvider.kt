import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.Lakshan.taskfolio.MainActivity
import com.Lakshan.taskfolio.R

class TaskAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            // Fetch tasks from SharedPreferences
            val tasks = getTasks(context)

            // Format tasks for display
            val widgetText = if (tasks.isNotEmpty()) {
                tasks.joinToString("\n") // Join tasks with newline
            } else {
                "No tasks added"
            }

            // Create an intent to launch MainActivity
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.activity_widget_layout)
            views.setTextViewText(R.id.timeTextView3, widgetText)

            // Set up click listener to open MainActivity
            views.setOnClickPendingIntent(R.id.timeTextView3, pendingIntent)

            // Tell the AppWidgetManager to perform the update
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private fun getTasks(context: Context): List<String> {
            val sharedPreferences = context.getSharedPreferences("tasks", Context.MODE_PRIVATE)
            val taskList = sharedPreferences.getString("task_list", "") ?: ""
            return taskList.split(",").filter { it.isNotBlank() }
        }
    }
}
