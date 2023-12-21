package com.example.smartalarm.Guide.Model.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.smartalarm.Guide.Model.Alarm
import com.example.smartalarm.Guide.Model.AlarmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application) : AndroidViewModel(application){
    private var repository: AlarmRepository
    var list : LiveData<List<Alarm>>

    init {

        Log.d("AlarmViewModel", "Creating AlarmViewModel")
        repository = AlarmRepository(application)
        list = repository.list
    }

    fun insert(alarm: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(alarm)
        }
    }

    fun delete(alarm: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(alarm)
        }
    }
    fun update(alarm: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(alarm)
        }
    }
}