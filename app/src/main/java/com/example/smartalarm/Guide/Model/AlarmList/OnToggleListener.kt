package com.example.smartalarm.Guide.Model.AlarmList

import com.example.smartalarm.Guide.Model.Alarm

interface OnToggleListener {
    fun onToggle(alarm: Alarm)
}