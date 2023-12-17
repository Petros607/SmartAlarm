package com.example.smartalarm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartalarm.AddingAlarm
import com.example.smartalarm.R
import com.google.gson.Gson

class AlarmActivity : AppCompatActivity() {
    private var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val buttonAlarmOff = findViewById<Button>(R.id.buttonAlarmOff)
        val buttonAlarmOff2 = findViewById<Button>(R.id.buttonAlarmOff2)

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
}

