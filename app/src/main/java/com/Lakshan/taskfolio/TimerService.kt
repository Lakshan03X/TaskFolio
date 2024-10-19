package com.Lakshan.taskfolio

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.Timer
import java.util.TimerTask

class TimerService : Service() {
    private val timer = Timer()
    private var shouldRun = true // Flag to control the timer

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time = intent.getDoubleExtra(TIME_EXTRA, 0.0)
        shouldRun = true // Set flag to true when starting the service
        Log.d("TimerService", "Service started with time: $time")
        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d("TimerService", "Service destroyed, stopping timer.")
        shouldRun = false // Set flag to false to stop the timer task
        timer.cancel()
        super.onDestroy()
    }

    private inner class TimeTask(private var time: Double) : TimerTask() {
        override fun run() {
            if (!shouldRun) {
                Log.d("TimerService", "Timer stopped.")
                cancel()
            }
            time++
            Log.d("TimerService", "Time incremented: $time")
            val intent = Intent(TIMER_UPDATE)
            intent.putExtra(TIME_EXTRA, time)
            sendBroadcast(intent)
        }
    }

    companion object {
        const val TIMER_UPDATE = "timerUpdated"
        const val TIME_EXTRA = "timeExtra"
    }
}
