package com.example.smartalarm.Guide.Model.CreateAlarm


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.smartalarm.Guide.Model.Alarm
import com.example.smartalarm.Guide.Model.ViewModel.AlarmViewModel
import com.example.smartalarm.R
import com.example.smartalarm.databinding.FragmentCreateBinding
import java.util.Calendar
import kotlin.time.Duration.Companion.milliseconds


class CreateFragment : Fragment() {
    lateinit var binding: FragmentCreateBinding
    lateinit var viewModel: AlarmViewModel
    private lateinit var editText: EditText
    private lateinit var uri: Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        editText = binding.userValue
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
        editText = binding.userValue

        uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        binding.buttonSelectMusic.setOnClickListener {
            showRingtonePicker(uri)
        }


        binding.buttonSave.setOnClickListener {
            alarm.hour = TimePickerUtil.getHour(binding.timePicker)
            alarm.minute = TimePickerUtil.getMinute(binding.timePicker)
            if (editText.text.toString() != "") {
                alarm.name = editText.text.toString()
            }
//            val ringtone = RingtoneManager.getRingtone(context, uri)
//            ringtone.play()
            alarm.ringtoneUri = uri.toString()
//            val parsedRingtone = Uri.parse(alarm?.ringtoneUri)
//            val ringtone = RingtoneManager.getRingtone(context, parsedRingtone)
//            ringtone.play()
            //alarm.start = true
            alarm.schedule(requireContext())
            viewModel.insert(alarm)
            Navigation.findNavController(binding.buttonSave).navigate(com.example.smartalarm.R.id.action_createFragment_to_navigationAlarm)
        }
        binding.buttonCancel.setOnClickListener {
            Navigation.findNavController(binding.buttonCancel).navigate(com.example.smartalarm.R.id.action_createFragment_to_navigationAlarm)
        }
    }

    private fun showRingtonePicker(uri: Uri) {
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
            putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Выберите мелодию")
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
            putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
            putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri)
        }
        startActivityForResult(intent, 228)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 228 && resultCode == RESULT_OK) {
            val newUri = data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            if (newUri != null) {
                uri = newUri
                val ringtoneName = RingtoneManager.getRingtone(context, uri).getTitle(context)
                binding.buttonSelectMusic.text = ringtoneName
            }
        }
    }
}