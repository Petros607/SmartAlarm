package com.example.smartalarm.colorGame

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartalarm.colorGame.utils.Constant
import com.example.smartalarm.colorGame.utils.Shared
import com.example.smartalarm.R


class GameOverActivity : AppCompatActivity(), View.OnClickListener {
    private var mMediaPlayer: MediaPlayer? = null
    private var value : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.btn_sound)
        findViewById<View>(R.id.btnRestart).setOnClickListener(this)
        val t4 = findViewById<View>(R.id.textView4) as TextView
        val t5 = findViewById<View>(R.id.textView5) as TextView
        val t7 = findViewById<View>(R.id.textView7) as TextView
        val t8 = findViewById<View>(R.id.textView8) as TextView
        val t9 = findViewById<View>(R.id.textView9) as TextView
        val t10 = findViewById<View>(R.id.textView10) as TextView
        t4.typeface = Shared.appfontLight
        t5.typeface = Shared.appfontLight
        t7.typeface = Shared.appfont
        t9.typeface = Shared.appfont
        t8.typeface = Shared.appfontLight
        t10.typeface = Shared.appfontLight
        val extras = intent.extras
        if (extras != null) {
            value = extras.getInt(Constant.CURRENT_SCORE, 0)
            if (value >= 12) {
                t5.text = "PASSED"
                t5.setTextColor(Color.GREEN)
            }
            t8.text = value.toString()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onClick(v: View) {
        mMediaPlayer!!.start()
        if (value < 12) {
            val intent = Intent(this, GameMainActivity::class.java)
            startActivity(intent)
            finish()
        }
        finish()
    }
}