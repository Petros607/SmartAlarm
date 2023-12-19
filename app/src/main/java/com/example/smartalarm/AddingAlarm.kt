package com.example.smartalarm

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.util.Calendar


data class Alarm(
    val name: String,
    val ringtoneUri: String,
    val timeInMillis: Long
)

class AddingAlarm : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var selectedCalendar: Calendar

    companion object {
        const val REQUEST_RINGTONE = 123
        const val PREFS_NAME = "MyPrefs"
        const val PREF_SELECTED_RINGTONE = "selectedRingtone"
        const val PREF_SELECTED_ALARM = "selectedAlarm"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adding_alarm)

        editText = findViewById(R.id.userValue)
        val buttonSelectTime = findViewById<Button>(R.id.button_select_time)
        val buttonSelectMusic = findViewById<Button>(R.id.button_select_music)
        val saveButton = findViewById<Button>(R.id.button_save)
        val buttonCancel = findViewById<Button>(R.id.button_cancel)
        val actionBar = supportActionBar
        actionBar?.title = "Установка будильника"

        // Инициализация Calendar
        selectedCalendar = Calendar.getInstance()

        // Обработчик нажатия для выбора времени
        buttonSelectTime.setOnClickListener {
            showTimePicker()
        }
        // Обработчик нажатия для выбора мелодии
        buttonSelectMusic.setOnClickListener {
            showRingtonePicker()
        }
        // Обработчик нажатия для сохранения
        saveButton.setOnClickListener {
            save()
        }
        buttonCancel.setOnClickListener {
            cancel()
        }
    }

    // Метод для отображения TimePickerDialog
    private fun showTimePicker() {
        val timePickerDialog = TimePickerDialog(
            this,
            { _: TimePicker, hourOfDay: Int, minute: Int ->
                // Установка выбранного времени в Calendar
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedCalendar.set(Calendar.MINUTE, minute)

                findViewById<Button>(R.id.button_select_time).text = String.format("%02d:%02d", selectedCalendar.get(Calendar.HOUR_OF_DAY), selectedCalendar.get(Calendar.MINUTE))

            },
            selectedCalendar.get(Calendar.HOUR_OF_DAY),
            selectedCalendar.get(Calendar.MINUTE),
            true // Включает 24-часовой формат времени
        )
        timePickerDialog.show()
        // В методе showTimePicker(), после установки времени в Calendar
    }

    private fun save() {
        val text = "Ошибка!"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, text, duration)

        var textToSave = editText.text.toString()
        if (editText.text.toString() == "") {textToSave = "Просыпайся"}

        val alarm = Alarm(
            name = textToSave,
            ringtoneUri = getSavedRingtone().toString(),
            timeInMillis = selectedCalendar.timeInMillis
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmClockInfo = AlarmManager.AlarmClockInfo(
            selectedCalendar.timeInMillis,
            getAlarmInfoPendingIntent()
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent())
                    val alarmJson = Gson().toJson(alarm)

                    getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                        .edit()
                        .putString(PREF_SELECTED_ALARM, alarmJson)
                        .apply()

                    val resultIntent = Intent()
                    resultIntent.putExtra("data", alarmJson)
                    setResult(Activity.RESULT_OK, resultIntent)

                    finish()
                } else {
                    val resultIntent = Intent()
                    setResult(Activity.RESULT_CANCELED, resultIntent)
                    toast.show()
            }
        }

    }

    private fun getSavedRingtone(): Uri? {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val savedRingtone = prefs.getString(PREF_SELECTED_RINGTONE, null)
        return savedRingtone?.let { Uri.parse(it) }
    }

    private fun showRingtonePicker() {
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
            putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Выберите мелодию")
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
            putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
        }
        startActivityForResult(intent, REQUEST_RINGTONE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RINGTONE && resultCode == RESULT_OK) {
            data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                ?.let { saveRingtone(it) }
        }
    }

    private fun saveRingtone(uri: Uri) {
        val ringtone = RingtoneManager.getRingtone(this, uri)
        val ringtoneName = ringtone.getTitle(this)
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            .edit()
            .putString(PREF_SELECTED_RINGTONE, uri.toString())
            .apply()
        val buttonSelectMusic = findViewById<Button>(R.id.button_select_music)
        buttonSelectMusic.text = ringtoneName
    }
    fun getAlarmInfoPendingIntent(): PendingIntent {
        // Получаем PendingIntent для отображения информации о будильнике
        val alarmInfoIntent = Intent(this, BlankFragmentAlarm::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        return PendingIntent.getActivity(
            this,
            0,
            alarmInfoIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun getAlarmActionPendingIntent(): PendingIntent {
        // Получаем PendingIntent для активности, которая будет запущена при срабатывании будильника
        val intent = Intent(this, AlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        return PendingIntent.getActivity(
            this,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun cancel(){
        finish()
    }
}



