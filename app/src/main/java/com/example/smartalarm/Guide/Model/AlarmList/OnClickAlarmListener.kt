package com.example.smartalarm.Guide.Model.AlarmList

import com.example.smartalarm.Guide.Model.Alarm

interface OnClickAlarmListener {
    fun onClick(alarm: Alarm)

    fun onLongClick(alarm: Alarm)
}