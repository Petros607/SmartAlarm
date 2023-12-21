package com.example.smartalarm.Guide.Model.AlarmList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartalarm.Guide.Model.ViewModel.AlarmViewModel
import com.example.smartalarm.R
import com.example.smartalarm.databinding.FragmentAlarmListBinding


class AlarmListFragment : Fragment() {

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
        //viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]

        //viewModel.list.observe(viewLifecycleOwner, Observer { adapter.setData(it) })

        adapter = AdapterAlarm()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }
}