package com.example.smartalarm.Guide.Model.BroadCastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Build
import android.util.Log
import com.example.smartalarm.Guide.Model.Alarm
import com.example.smartalarm.Guide.Model.Service.AlarmService
import java.util.Calendar


class AlarmBroadCastReceiver : BroadcastReceiver() {
    companion object {
        val FRIDAY = "FRIDAY"
        val TITLE = "TITLE"
        val RECURRING = "RECURRING"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val intent = intent
        val intentService = Intent(context, AlarmService::class.java)
        //val intent = getIntent()
        //val ringtoneUriString = intent?.getStringExtra("ALARM")
        //intentService.putExtra("ALARM", ringtoneUriString)
        val alarm = intent?.getParcelableExtra<Alarm>("ALARM")
        intentService.putExtra("ALARM", alarm)


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