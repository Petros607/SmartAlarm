package com.example.smartalarm

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.smartalarm.colorGame.SplashActivity
import kotlin.random.Random

class BlankFragmentSettings : Fragment() {

    companion object {
        fun newInstance() = BlankFragmentSettings()
    }

    private lateinit var viewModel: BlankFragmentSettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank_settings, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dino: Button = view.findViewById(R.id.button_dino)
        val colGame: Button = view.findViewById(R.id.button_colors)
        lateinit var intent: Intent
        dino.setOnClickListener {
            intent = Intent(requireContext(), DinoActivity::class.java)
            startActivity(intent)
        }
        colGame.setOnClickListener {
            intent = Intent(requireContext(), SplashActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BlankFragmentSettingsViewModel::class.java)
    }

}