package com.example.smartalarm

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cn.iwgang.countdownview.CountdownView

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

        //New
        val stopwatch_play_pause: ImageView = view.findViewById(R.id.stopwatch_play_pause)
        val stopwatch_reset: ImageView = view.findViewById(R.id.stopwatch_reset)
        stopwatch_play_pause.setOnClickListener {
            if (!isRunning) {
                setBaseTime()
                stopwatch.start()
                isRunning = true
                stopwatch_reset.visibility = View.VISIBLE
                stopwatch_play_pause.setImageResource(R.drawable.ic_pause_vector)
            }
            else {
                saveOffset()
                stopwatch.stop()
                isRunning = false
                stopwatch_play_pause.setImageResource(R.drawable.ic_play_vector)
                if (offset == 0.toLong()) {
                    stopwatch_reset.visibility = View.INVISIBLE
                }
            }
        }
        stopwatch_reset.setOnClickListener {
            offset = 0
            if (!isRunning) { stopwatch_reset.visibility = View.INVISIBLE }
            setBaseTime()
        }
    }
    private fun setBaseTime() {
        stopwatch.base = SystemClock.elapsedRealtime() - offset
//        gitStopwatch.start(SystemClock.elapsedRealtime() - offset)
        stopwatch.format = "%s:%s.%s"
    }

    private fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
        //offset = SystemClock.elapsedRealtime() - gitStopwatch.remainTime
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BlankFragmentStopwatchViewModel::class.java)
    }

}