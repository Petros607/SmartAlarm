package com.example.smartalarm.Guide.Model

import android.app.Application
import androidx.lifecycle.LiveData

class AlarmRepository {
    private var alarmDao : AlarmDao
    var list: LiveData<List<Alarm>>

    constructor(application: Application)
    {
        this.alarmDao = AlarmDatabase.getInstance(application).alarmDao()
        this.list = alarmDao.getAll()
    }

    suspend fun insert(alarm: Alarm) {
        alarmDao.insert(alarm)
    }
    suspend fun delete(alarm: Alarm) {
        alarmDao.delete(alarm)
    }
    suspend fun update(alarm: Alarm) {
        alarmDao.update(alarm)
    }

}