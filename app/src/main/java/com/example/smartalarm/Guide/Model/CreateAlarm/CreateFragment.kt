package com.example.smartalarm.Guide.Model.CreateAlarm


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.smartalarm.Guide.Model.Alarm
import com.example.smartalarm.Guide.Model.ViewModel.AlarmViewModel
import com.example.smartalarm.databinding.FragmentCreateBinding
import java.util.Calendar
import kotlin.time.Duration.Companion.milliseconds


class CreateFragment : Fragment() {
    lateinit var binding: FragmentCreateBinding
    lateinit var viewModel: AlarmViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateBinding.inflate(inflater, container, false)


//        val navView = view?.findViewById<BottomNavigationView>(R.id.nav)
//        if (navView != null) {
//            navView.visibility = View.GONE
//        }
//        supportActionBar?.hide()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alarm = Alarm()
        viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]
        binding.buttonSave.setOnClickListener {
            alarm.hour = TimePickerUtil.getHour(binding.timePicker)
            alarm.minute = TimePickerUtil.getMinute(binding.timePicker)
            //alarm.start = true
            alarm.schedule(requireContext())
            viewModel.insert(alarm)
            Navigation.findNavController(binding.buttonSave).navigate(com.example.smartalarm.R.id.action_createFragment_to_navigationAlarm)
        }
    }
}