package com.example.smartalarm.Guide.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    @PrimaryKey val uid : Long?,
    @ColumnInfo var hour : Int,
    @ColumnInfo var minute : Int,
    @ColumnInfo var start : Boolean
) {
    constructor(id: Long, hour: Int, minute: Int): this (
        id, hour, minute, start = false
    )

    fun getTime(): String {
        return "$hour:$minute"
    }


}
