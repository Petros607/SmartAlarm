package com.example.smartalarm.Guide.Model.Service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.smartalarm.Guide.Model.Alarm
import com.example.smartalarm.Guide.Model.AlarmActivity
import com.example.smartalarm.Guide.Model.Application.App
import com.example.smartalarm.R

class AlarmService: Service() {
    //private val audioController = AudioController()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val intent2 = intent
        val alarm = intent2?.getParcelableExtra<Alarm>("ALARM")

        val intent = Intent(this, AlarmActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, App.ID)
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setContentTitle(alarm?.name)
            .setContentText("Просыпайся, дорогой")
            .setContentIntent(pendingIntent)
            .build()

        startActivity(intent)

        startForeground(1, notification)

        (application as App).vibrationController.startVibration(applicationContext)


        //val ringtoneUriString = intent2?.getStringExtra("ALARM")
        val ringtoneUriString = alarm?.ringtoneUri
        (application as App).audioController.startAudio(applicationContext, ringtoneUriString)
        intent.putExtra("ALARM", alarm)
        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}