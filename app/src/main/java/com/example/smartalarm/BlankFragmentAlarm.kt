package com.example.smartalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.Calendar
import com.google.android.material.timepicker.TimeFormat as TimeFormat1

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
            startActivity(intent)
        }

        return rootView
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