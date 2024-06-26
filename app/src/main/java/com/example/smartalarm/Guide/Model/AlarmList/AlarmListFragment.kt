package com.example.smartalarm.Guide.Model.AlarmList

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartalarm.Guide.Model.Alarm
import com.example.smartalarm.Guide.Model.Service.AlarmService
import com.example.smartalarm.Guide.Model.ViewModel.AlarmViewModel
import com.example.smartalarm.R
import com.example.smartalarm.databinding.FragmentAlarmListBinding


class AlarmListFragment : Fragment(), OnToggleListener, OnClickAlarmListener {

    private lateinit var binding: FragmentAlarmListBinding
    private lateinit var adapter: AdapterAlarm
    private lateinit var viewModel: AlarmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlarmListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        //inflater.inflate(R.layout.fragment_blank_alarm, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]

        adapter = AdapterAlarm()
        adapter.addOnToggleListener(this)
        adapter.addOnClickAlarmListener(this)
        viewModel.list.observe(viewLifecycleOwner, Observer { adapter.setData(it) })


        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.buttonSetAlarm.setOnClickListener {
            Navigation.findNavController(binding.buttonSetAlarm).navigate(R.id.action_navigationAlarm_to_createFragment)

        }
    }

    override fun onToggle(alarm: Alarm) {
        if (alarm.start) {
            alarm.schedule(requireContext())
        } else {
            alarm.cancel(requireContext())
        }
    }

    override fun onClick(alarm: Alarm) {

    }

    override fun onLongClick(alarm: Alarm) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Удаление будильника")
            .setMessage("Вы точно хотите удалить будильник?")
            .setNegativeButton("Нет((") { dialog, which -> dialog.dismiss()}
            .setPositiveButton("Конечно") {dialog, which ->
                if (alarm.start) {
                    alarm.cancel(requireContext())
                }
                viewModel.delete(alarm)
            }
        dialog.show()
    }
}