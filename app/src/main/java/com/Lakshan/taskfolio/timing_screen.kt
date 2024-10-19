package com.Lakshan.taskfolio

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.Lakshan.taskfolio.databinding.ActivityTimingScreenBinding
import java.util.Calendar
import java.util.Date
import kotlin.math.roundToInt

class timing_screen : AppCompatActivity() {

    private lateinit var binding: ActivityTimingScreenBinding
    private var timerStated = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTimingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("timing_screen", "onCreate called, initializing components")

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, add_screen::class.java)
            startActivity(intent)
        }

        binding.timerGoBtn.setOnClickListener {
            val intent = Intent(this, timing_screen::class.java)
            startActivity(intent)
        }

        binding.setingBtn.setOnClickListener {
            val intent = Intent(this, setting_screen::class.java)
            startActivity(intent)
        }

        binding.homeBtn.setOnClickListener {
            val intent = Intent(this, Home_Screen::class.java)
            startActivity(intent)
        }

        createNotificationChannel()

        val timePicker: TimePicker = findViewById(R.id.timePicker)
        val setReminderButton: Button = findViewById(R.id.setReminderButton)

        // Timer Start/Stop Logic
        binding.startBtn.setOnClickListener {
            Log.d("timing_screen", "Start button clicked")
            startStopTimer()
        }
        binding.resetBtn.setOnClickListener {
            Log.d("timing_screen", "Reset button clicked")
            resetTimer()
        }

        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATE), RECEIVER_NOT_EXPORTED)

        setReminderButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            Log.d("timing_screen", "Setting reminder for: ${Date(calendar.timeInMillis)}")
            setReminder(calendar.timeInMillis)
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun setReminder(timeInMillis: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ReminderBroadcastReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
        Toast.makeText(this, "Reminder Set", Toast.LENGTH_SHORT).show()
    }

    private fun createNotificationChannel() {
        val name = "ReminderChannel"
        val descriptionText = "Channel for reminder notifications"
        val importance = android.app.NotificationManager.IMPORTANCE_HIGH
        val channel = android.app.NotificationChannel("reminderChannel", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: android.app.NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun startStopTimer() {
        if (timerStated)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer() {
        if (!timerStated) {
            Log.d("timing_screen", "Starting timer")
            serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
            startService(serviceIntent)
            binding.startBtn.text = "Stop"
            timerStated = true
        }
    }

    private fun stopTimer() {
        Log.d("timing_screen", "Stopping timer")
        stopService(serviceIntent)
        binding.startBtn.text = "Start"
        timerStated = false
    }

    private fun resetTimer() {
        Log.d("timing_screen", "Resetting timer")
        stopTimer()
        time = 0.0
        binding.timeTextView.text = getTimeStringFromDouble(time)
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            if (intent != null) {
                time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
                Log.d("timing_screen", "Time updated from service: $time")
            }
            binding.timeTextView.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d:%02d", hour, min, sec)

    override fun onDestroy() {
        unregisterReceiver(updateTime)
        super.onDestroy()
    }
}
