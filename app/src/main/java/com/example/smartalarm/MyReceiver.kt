//package com.example.smartalarm
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.media.MediaPlayer
//import android.net.Uri
//import android.provider.Settings
//
//class MyReceiver: BroadcastReceiver() {
//    override fun onReceive(context: Context?, intent: Intent?) {
//        val mp = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI)
//        mp.isLooping = true;
//        mp.start()
//        //val savedRingtoneUri = getSavedRingtone()
//
//        val activityIntent = Intent(context, AlarmActivity::class.java)
//        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//
//        // Запускаем активность
//        context?.startActivity(activityIntent)
//    }
////    private fun getSavedRingtone(): Uri? {
////        val prefs = getSharedPreferences(AddingAlarm.PREFS_NAME, Context.MODE_PRIVATE)
////        val savedRingtone = prefs.getString(AddingAlarm.PREF_SELECTED_RINGTONE, null)
////        return savedRingtone?.let { Uri.parse(it) }
////    }
//
//
//}