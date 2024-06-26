package com.example.smartalarm.Guide.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Alarm::class],version = 5, exportSchema = false)
abstract class AlarmDatabase: RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    companion object {
        private var INSTANCE : AlarmDatabase? = null
        fun getInstance(context: Context) : AlarmDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context, AlarmDatabase::class.java, context.packageName)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as AlarmDatabase
        }
    }

}