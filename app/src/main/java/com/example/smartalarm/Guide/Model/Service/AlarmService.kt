package com.example.smartalarm.Guide.Model.Service

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.smartalarm.Guide.Model.AlarmActivity
import com.example.smartalarm.Guide.Model.Application.App
import com.example.smartalarm.R

class AlarmService: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val intent = Intent(this, AlarmActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, App.ID)
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setContentTitle("Заголовок")
            .setContentText("Текст")
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}
class AlarmWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val intent = Intent(applicationContext, AlarmActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(applicationContext, App.ID)
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setContentTitle("Заголовок")
            .setContentText("Текст")
            .setContentIntent(pendingIntent)
            .build()

        // Здесь вы можете использовать вашу службу для установки уведомления в качестве фонового сервиса
        // Ваша служба может быть удалена, если она не используется
        return Result.success()
    }
}