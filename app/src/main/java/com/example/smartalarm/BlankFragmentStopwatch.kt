package com.example.smartalarm

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class BlankFragmentStopwatch : Fragment() {
    private lateinit var stopwatch: Chronometer
    private var isRunning = false // Включён ли секундомер
    private var offset: Long = 0 // базовое смещение
    companion object {
        fun newInstance() = BlankFragmentStopwatch()
    }

    private lateinit var viewModel: BlankFragmentStopwatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank_stopwatch, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stopwatch = view.findViewById(R.id.chronometer)

        val startButton: Button = view.findViewById(R.id.start_button)
        startButton.setOnClickListener {
            if (!isRunning) {
                setBaseTime()
                stopwatch.start()
                isRunning = true
            }
        }

        val pauseButton: Button = view.findViewById(R.id.pause_button)
        pauseButton.setOnClickListener {
            if (isRunning) {
                saveOffset()
                stopwatch.stop()
                isRunning = false
            }
        }

        val resetButton: Button = view.findViewById(R.id.reset_button)
        resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }
    private fun setBaseTime() {
        stopwatch.base = SystemClock.elapsedRealtime() - offset
        stopwatch.format = "%s:%s.%s"
    }

    private fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BlankFragmentStopwatchViewModel::class.java)
    }

}