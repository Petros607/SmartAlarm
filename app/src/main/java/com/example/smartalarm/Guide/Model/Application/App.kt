package com.example.smartalarm.Guide.Model.Application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

class App : Application() {

    companion object {
        val ID = "com.example.smartalarm.phucvr"
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        super.onCreate()
        createChannel()}
    }


    private fun createChannel() {
        val channel = NotificationChannel(ID, "Alarm Service", NotificationManager.IMPORTANCE_DEFAULT)

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}