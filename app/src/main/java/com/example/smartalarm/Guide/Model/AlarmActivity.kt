package com.example.smartalarm.Guide.Model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.smartalarm.DinoActivity
import com.example.smartalarm.Guide.Model.Application.App
import com.example.smartalarm.Guide.Model.BroadCastReceiver.AlarmBroadCastReceiver
import com.example.smartalarm.Guide.Model.Service.AlarmService
import com.example.smartalarm.MathActivity
import com.example.smartalarm.databinding.ActivityAlarmBinding
import kotlin.random.Random
import com.example.smartalarm.colorGame.GameMainActivity

class AlarmActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val intent = intent
        val alarm = intent?.getParcelableExtra<Alarm>("ALARM")

        if (alarm != null) {
            binding.textView.text = alarm.name
        }
        //val ringtoneUriString = intent?.getStringExtra("ALARM")

        binding.buttonAlarmOff.setOnClickListener {
            lateinit var intent: Intent
            when (Random.nextInt(2, 3)) {
                0 -> intent = Intent(this, MathActivity::class.java)
                1 -> intent = Intent(this, DinoActivity::class.java)
                2 -> intent = Intent(this, GameMainActivity::class.java)
            }
            startActivity(intent)
            binding.buttonAlarmOff.visibility = View.GONE
            binding.buttonAlarmOff2.visibility = View.VISIBLE
        }
        binding.buttonAlarmOff2.setOnClickListener {
            (application as App).audioController.stopAudio()
            (application as App).vibrationController.stopVibration()
            finish()
        }
        binding.buttonMin5.setOnClickListener {
            (application as App).audioController.stopAudio()
            (application as App).vibrationController.stopVibration()

            alarm?.minute = alarm?.minute?.plus(5)!!
            alarm.schedule(this)

            finish()

        }
    }

}