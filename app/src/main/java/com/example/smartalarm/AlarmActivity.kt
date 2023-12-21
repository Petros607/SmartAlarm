//package com.example.smartalarm
//
//import android.app.AlarmManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.content.SharedPreferences
//import android.os.Vibrator
//import android.media.Ringtone
//import android.media.RingtoneManager
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.os.Handler
//import android.view.View
//import android.widget.Button
//import android.widget.ImageButton
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.smartalarm.AddingAlarm
//import android.os.VibrationEffect
//import com.google.gson.Gson
//
//class AlarmActivity : AppCompatActivity() {
//    private var ringtone: Ringtone? = null
//    private lateinit var vibrator: Vibrator
//
//    private val handler = Handler()
//    private val checkRingtoneRunnable = object : Runnable {
//        override fun run() {
//            checkRingtonePlaying()
//            startVibration()
//            handler.postDelayed(this, 2000) // Повторить через определенный интервал
//        }
//    }
//    private fun checkRingtonePlaying() {
//        if (ringtone?.isPlaying == false) {
//            ringtone?.play()
//        }
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_alarm)
//
//        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//
//        val buttonAlarmOff = findViewById<Button>(R.id.buttonAlarmOff)
//        val buttonAlarmOff2 = findViewById<Button>(R.id.buttonAlarmOff2)
//        val buttonMin5 = findViewById<ImageButton>(R.id.button_min5)
//        val actionBar = supportActionBar
//        actionBar?.hide()
//        startVibration()
//
//
//        val prefs = getSharedPreferences(AddingAlarm.PREFS_NAME, Context.MODE_PRIVATE)
//        val alarmJson = prefs.getString(AddingAlarm.PREF_SELECTED_ALARM, null)
//        buttonMin5.setOnClickListener {
//            transfer(alarmJson)
//        }
//        buttonAlarmOff.setOnClickListener {
//            val intent = Intent(this, MathActivity::class.java)
//            startActivity(intent)
//            buttonAlarmOff.visibility = View.GONE
//            buttonAlarmOff2.visibility = View.VISIBLE
//        }
//
//        buttonAlarmOff2.setOnClickListener {
//            stopAlarm()
//        }
//
//        val savedRingtoneUri = getSavedRingtone()
//        val alarmName = findViewById<TextView>(R.id.textView)
//
//        // Воспроизведение рингтона
//        if (savedRingtoneUri != null) {
//            // Создание объекта Ringtone с использованием URI
//            ringtone = RingtoneManager.getRingtone(this, savedRingtoneUri)
//
//            // Воспроизведение рингтона
//            ringtone?.play()
//            handler.postDelayed(checkRingtoneRunnable, 2000)
//        }
//
//        // Загрузка данных о будильнике
//        loadAlarmData(alarmName)
//    }
//
//    private fun stopAlarm() {
//        if (ringtone != null && ringtone!!.isPlaying) {
//            handler.removeCallbacks(checkRingtoneRunnable)
//            ringtone!!.stop()
//
//        }
//        vibrator.cancel()
//
//        finish()
//    }
//
//    private fun startVibration() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val vibrationEffect = VibrationEffect.createWaveform(longArrayOf(1000, 500, 1000), -1)
//            vibrator.vibrate(vibrationEffect)
//        } else {
//            // Для более старых устройств
//            vibrator.vibrate(1000)
//        }
//    }
//    private fun getSavedRingtone(): Uri? {
//        val prefs = getSharedPreferences(AddingAlarm.PREFS_NAME, Context.MODE_PRIVATE)
//        val savedRingtone = prefs.getString(AddingAlarm.PREF_SELECTED_RINGTONE, null)
//        return savedRingtone?.let { Uri.parse(it) }
//    }
//
//    private fun loadAlarmData(alarmNameTextView: TextView) {
//        val prefs = getSharedPreferences(AddingAlarm.PREFS_NAME, Context.MODE_PRIVATE)
//        val alarmJson = prefs.getString(AddingAlarm.PREF_SELECTED_ALARM, null)
//
//        if (alarmJson != null) {
//            // Если есть сохраненные данные о будильнике, загрузим их
//            val alarm = Gson().fromJson(alarmJson, Alarm::class.java)
//            alarmNameTextView.text = alarm.name
//        }
//    }
//
//    private fun transfer(alarmJson: String?) {
//        if (alarmJson != null) {
//            // Если есть сохраненные данные о будильнике, загрузим их
//            val alarm = Gson().fromJson(alarmJson, Alarm::class.java)
//            vibrator.cancel()
//
//            // Увеличиваем время на 5 минут
//            val newTimeMillis = alarm.timeInMillis + 5 * 60 * 1000 // 5 минут в миллисекундах
//
//            // Получаем PendingIntent для отображения информации о будильнике
//            val alarmInfoIntent = Intent(this, BlankFragmentAlarm::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//            }
//            val alarmInfoPendingIntent = PendingIntent.getActivity(
//                this,
//                0,
//                alarmInfoIntent,
//                PendingIntent.FLAG_IMMUTABLE
//            )
//
//            // Устанавливаем новое время для будильника с использованием PendingIntent
//            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            val alarmClockInfo = AlarmManager.AlarmClockInfo(
//                newTimeMillis,
//                alarmInfoPendingIntent
//            )
//            val text = "Ошибка!"
//            val duration = Toast.LENGTH_SHORT
//
//            val toast = Toast.makeText(applicationContext, text, duration)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//
//                if (alarmManager.canScheduleExactAlarms()) {
//                    alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent())
//                    val alarmJson = Gson().toJson(alarm)
//
//                    getSharedPreferences(AddingAlarm.PREFS_NAME, MODE_PRIVATE)
//                        .edit()
//                        .putString(AddingAlarm.PREF_SELECTED_ALARM, alarmJson)
//                        .apply()
//
//                    finish()
//                } else {
//                    toast.show()
//                }
//            }
//
//            // Останавливаем звучание текущего будильника
//            stopAlarm()
//
//            // Закрываем текущую активность
//            finish()
//        }}
//
//        fun getAlarmActionPendingIntent(): PendingIntent {
//            // Получаем PendingIntent для активности, которая будет запущена при срабатывании будильника
//            val intent = Intent(this, AlarmActivity::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//            }
//            return PendingIntent.getActivity(
//                this,
//                1,
//                intent,
//                PendingIntent.FLAG_IMMUTABLE
//            )
//        }
//}
//
