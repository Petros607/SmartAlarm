package com.example.smartalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartalarm.AddingAlarm
import com.google.gson.Gson

class AlarmActivity : AppCompatActivity() {
    private var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val buttonAlarmOff = findViewById<Button>(R.id.buttonAlarmOff)
        val buttonAlarmOff2 = findViewById<Button>(R.id.buttonAlarmOff2)
        val buttonMin5 = findViewById<ImageButton>(R.id.button_min5)
        val actionBar = supportActionBar
        actionBar?.hide()


        val prefs = getSharedPreferences(AddingAlarm.PREFS_NAME, Context.MODE_PRIVATE)
        val alarmJson = prefs.getString(AddingAlarm.PREF_SELECTED_ALARM, null)
        buttonMin5.setOnClickListener {
            transfer(alarmJson)
        }
        buttonAlarmOff.setOnClickListener {
            val intent = Intent(this, MathActivity::class.java)
            startActivity(intent)
            buttonAlarmOff.visibility = View.GONE
            buttonAlarmOff2.visibility = View.VISIBLE
        }

        buttonAlarmOff2.setOnClickListener {
            stopAlarm()
        }

        val savedRingtoneUri = getSavedRingtone()
        val alarmName = findViewById<TextView>(R.id.textView)

        // Воспроизведение рингтона
        if (savedRingtoneUri != null) {
            // Создание объекта Ringtone с использованием URI
            ringtone = RingtoneManager.getRingtone(this, savedRingtoneUri)

            // Воспроизведение рингтона
            ringtone?.play()
        }

        // Загрузка данных о будильнике
        loadAlarmData(alarmName)
    }

    private fun stopAlarm() {
        if (ringtone != null && ringtone!!.isPlaying) {
            ringtone!!.stop()
        }

        finish()
    }

    private fun getSavedRingtone(): Uri? {
        val prefs = getSharedPreferences(AddingAlarm.PREFS_NAME, Context.MODE_PRIVATE)
        val savedRingtone = prefs.getString(AddingAlarm.PREF_SELECTED_RINGTONE, null)
        return savedRingtone?.let { Uri.parse(it) }
    }

    private fun loadAlarmData(alarmNameTextView: TextView) {
        val prefs = getSharedPreferences(AddingAlarm.PREFS_NAME, Context.MODE_PRIVATE)
        val alarmJson = prefs.getString(AddingAlarm.PREF_SELECTED_ALARM, null)

        if (alarmJson != null) {
            // Если есть сохраненные данные о будильнике, загрузим их
            val alarm = Gson().fromJson(alarmJson, Alarm::class.java)
            alarmNameTextView.text = alarm.name
        }
    }

    private fun transfer(alarmJson: String?) {
        if (alarmJson != null) {
            // Если есть сохраненные данные о будильнике, загрузим их
            val alarm = Gson().fromJson(alarmJson, Alarm::class.java)

            // Увеличиваем время на 5 минут
            val newTimeMillis = alarm.timeInMillis + 5 * 60 * 1000 // 5 минут в миллисекундах

            // Получаем PendingIntent для отображения информации о будильнике
            val alarmInfoIntent = Intent(this, BlankFragmentAlarm::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val alarmInfoPendingIntent = PendingIntent.getActivity(
                this,
                0,
                alarmInfoIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            // Устанавливаем новое время для будильника с использованием PendingIntent
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmClockInfo = AlarmManager.AlarmClockInfo(
                newTimeMillis,
                alarmInfoPendingIntent
            )
            //alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent())

            // Останавливаем звучание текущего будильника
            stopAlarm()

            // Закрываем текущую активность
            finish()
        }}

        fun getAlarmActionPendingIntent(): PendingIntent {
            // Получаем PendingIntent для активности, которая будет запущена при срабатывании будильника
            val intent = Intent(this, AlarmActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            return PendingIntent.getActivity(
                this,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
}

