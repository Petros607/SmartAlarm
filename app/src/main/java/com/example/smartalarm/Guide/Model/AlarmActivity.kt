package com.example.smartalarm.Guide.Model

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.smartalarm.Guide.Model.Service.AlarmService
import com.example.smartalarm.databinding.ActivityAlarmBinding

class AlarmActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.buttonAlarmOff.setOnClickListener {
            val intent = Intent(this, AlarmService::class.java)
            applicationContext.stopService(intent)
            finish()
        }
    }
}