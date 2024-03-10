package com.example.smartalarm.Guide.Model.Service

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class VibrationController {
    private var vibrator: Vibrator? = null

    fun startVibration(context: Context) {
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createWaveform(longArrayOf(1000,500,1000),1))
        } else {
            vibrator?.vibrate(1000)
        }
    }

    fun stopVibration() {
        vibrator?.cancel()
    }
}
