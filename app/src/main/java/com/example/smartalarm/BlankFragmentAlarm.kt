package com.example.smartalarm

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import java.util.Date
import java.util.Locale


class BlankFragmentAlarm : Fragment() {

    private lateinit var viewModel: BlankFragmentAlarmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_blank_alarm, container, false)
        val buttonSetAlarm = rootView.findViewById<Button>(R.id.button_set_alarm)
        buttonSetAlarm?.setOnClickListener {
            val intent = Intent(requireContext(), AddingAlarm::class.java)
            startActivityForResult(intent, 228)
        }

        return rootView
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 228 && resultCode == Activity.RESULT_OK) {
            val alarmJson = data?.getStringExtra("data")
            if (alarmJson != null) {
                // Обработка полученных данных
                val gson = Gson()
                val alarm = gson.fromJson(alarmJson, Alarm::class.java)

                var name = alarm.name
                val timeInMillis = alarm.timeInMillis
                val formattedTime = formatTime(timeInMillis)
                val textSetAlarmYes = view?.findViewById<TextView>(R.id.textAlarmSetYes)
                if (name == "Просыпайся") {name = "Будильник"}
                textSetAlarmYes?.text = "$name\t\t $formattedTime"
                textSetAlarmYes?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))


                val text = "Будильник установлен!"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(requireActivity(), text, duration)
                toast.show()
                }
            }
        else if (requestCode == 228 && resultCode == Activity.RESULT_CANCELED) {
            val text = "Будильник не установлен!"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(requireActivity(), text, duration)
        toast.show()
            }
        }
    private fun formatTime(timeInMillis: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = Date(timeInMillis)
        return dateFormat.format(date)
    }



    private fun getAlarmInfoPendingIntent(): PendingIntent {
        val alarmInfoIntent = Intent(requireContext(), BlankFragmentAlarm::class.java)
        alarmInfoIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(
            requireContext(),
            0,
            alarmInfoIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
    private fun getAlarmActionPendingIntent(): PendingIntent {
        val intent = Intent(requireContext(), AlarmActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(requireContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BlankFragmentAlarmViewModel::class.java)
        // TODO: Use the ViewModel
    }

}