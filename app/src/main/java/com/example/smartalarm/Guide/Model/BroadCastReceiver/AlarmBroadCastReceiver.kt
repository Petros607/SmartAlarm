package com.example.smartalarm.Guide.Model.BroadCastReceiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.smartalarm.Guide.Model.Service.AlarmService
import com.example.smartalarm.Guide.Model.Service.AlarmWorker
import java.util.Calendar

class AlarmBroadCastReceiver : BroadcastReceiver() {
    companion object {
        val FRIDAY = "FRIDAY"
        val TITLE = "TITLE"
        val RECURRING = "RECURRING"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val intentService = Intent(context, AlarmService::class.java)
        //if (!intent?.getBooleanExtra(RECURRING, false)!!) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context?.startForegroundService(intentService)
                Log.d("TAG", "Debug message1");
//                val request = OneTimeWorkRequest.Builder(AlarmWorker::class.java).addTag("ALARM_WORKER_TAG").build()
//                if (context != null) {
//                    WorkManager.getInstance(context).enqueue(request)
//                }
            } else {
                //context?.startService(intentService)
                context?.startForegroundService(intentService)
            }
        //}
//        else {
//            if (isToday(intent)) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    Log.d("TAG", "Debug message2");
//                    //context?.startForegroundService(intentService)
//                    val request = OneTimeWorkRequest.Builder(AlarmWorker::class.java).addTag("ALARM_WORKER_TAG").build()
//                    if (context != null) {
//                        WorkManager.getInstance(context).enqueue(request)
//                    }
//                } else {
//                    context?.startService(intentService)
//                }
//            }
//        }
    }

    //Не успеваю реализовать дни недели повтор
    private fun isToday(intent: Intent): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val today = calendar.get(Calendar.DAY_OF_WEEK)
        when (today) {
            Calendar.FRIDAY -> {
                if (intent.getBooleanExtra(FRIDAY, false))
                    return true
                return false
            }
        }
        return false
    }
}