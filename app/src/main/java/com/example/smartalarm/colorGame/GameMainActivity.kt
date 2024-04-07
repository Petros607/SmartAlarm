package com.example.smartalarm.colorGame

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.smartalarm.R
import com.example.smartalarm.colorGame.utils.Shared


class GameMainActivity : AppCompatActivity(), View.OnClickListener {
    private var isFirstime = true
    private var mMediaPlayer: MediaPlayer? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.btn_sound)
        setContentView(R.layout.game_activity_main)
        findViewById<View>(R.id.btnPlay).setOnClickListener(this)
        val t1 = findViewById<TextView>(R.id.textView)
        val t2 = findViewById<TextView>(R.id.textView2)
        val t3 = findViewById<TextView>(R.id.textView3)
        t1.typeface = Shared.appfontLight
        t2.typeface = Shared.appfontLight
        t3.typeface = Shared.appfontLight
    }

    override fun onClick(v: View) {
        mMediaPlayer!!.start()
        var intent: Intent? = null
        when (v.id) {
            R.id.btnPlay -> intent = Intent(this, GameActivity::class.java)
        }
        if (intent != null) {
            ContextCompat.startActivity(this, intent, null)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        finish()
    }

    protected override fun onResume() {
        super.onResume()
    }

    protected override fun onDestroy() {
        super.onDestroy()
    }
}