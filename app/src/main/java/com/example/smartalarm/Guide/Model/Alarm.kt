package com.example.smartalarm.Guide.Model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smartalarm.Guide.Model.BroadCastReceiver.AlarmBroadCastReceiver
import java.lang.StringBuilder
import java.util.Calendar
import java.util.Random

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true) val uid : Long?,
    @ColumnInfo var hour : Int,
    @ColumnInfo var minute : Int,
    @ColumnInfo var start : Boolean,
    @ColumnInfo var fri : Boolean
) {
    constructor(id: Long, hour: Int, minute: Int): this (
        id, hour, minute, start = false, true
    )

    constructor(): this (
        Random().nextLong(), 0, 0, false, true
    )

    fun getTime(): String {
        return "$hour:$minute"
    }

    fun getRepeat(): String {
        val builder = StringBuilder()
        if (fri) {
            builder.append("Fri")
        }
        return builder.toString()
    }

    fun schedule(context: Context) {
        val alarmManager: AlarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadCastReceiver::class.java)

        intent.putExtra(AlarmBroadCastReceiver.FRIDAY, fri)
        intent.putExtra(AlarmBroadCastReceiver.RECURRING, isLoop())

        val pendingIntent = PendingIntent.getBroadcast(context,uid?.toInt()!!,intent, PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK)+1)
        }
        val oneDay : Long = 24*60*60*1000
        if (!isLoop()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
        } else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, oneDay, pendingIntent)
        }
        start = true


    }

    fun cancel (context: Context) {
        val alarmManager : AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadCastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,uid?.toInt()!!,intent,PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
        start = false
    }
    //Тоже на дни недели (типа повторение), но еще пригодится
    private fun isLoop(): Boolean {
        return false
    }


}
