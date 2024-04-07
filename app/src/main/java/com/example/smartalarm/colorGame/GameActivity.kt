package com.example.smartalarm.colorGame

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.os.Bundle

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartalarm.R
import com.example.smartalarm.colorGame.utils.Constant
import com.example.smartalarm.colorGame.utils.Shared
import com.example.smartalarm.colorGame.widget.FlowLayout
import com.example.smartalarm.colorGame.widget.FlowLayout.LayoutParam
import java.util.Random


@Suppress("DEPRECATION")
class GameActivity : AppCompatActivity(), View.OnClickListener {
    private var currentScore = 0
    private var currentblock = Constant.BLOCK_2
    private var currentlight = Constant.LIGHT_EASY
    private var txtScore: TextView? = null
    private var viewTimer: ProgressBar? = null
    private var timer: CountDownTimer? = null
    private var flow: FlowLayout? = null
    private var mMediaPlayer: MediaPlayer? = null
    private var mMediaPlayerFail: MediaPlayer? = null
    private var isFirstimeTouch = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        mMediaPlayerFail = MediaPlayer.create(applicationContext, R.raw.fail)
        val t6 = findViewById<View>(R.id.textView6) as TextView
        txtScore = findViewById<View>(R.id.txtscore) as TextView
        viewTimer = findViewById<View>(R.id.timer) as ProgressBar
        flow = findViewById<View>(R.id.flowLayout) as FlowLayout
        t6.typeface = Shared.appfont
        txtScore!!.typeface = Shared.appfontBold
        txtScore!!.text = currentScore.toString()
        flow!!.post {
            val width = flow!!.width
            val params = flow!!.layoutParams
            params.width = width
            params.height = params.width
            flow!!.layoutParams = params
            gameStart()
        }
        viewTimer!!.progress = 100
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun gameOver() {
        mMediaPlayerFail!!.start()
        val intent = Intent(this, GameOverActivity::class.java)
        intent.putExtra(Constant.CURRENT_SCORE, currentScore)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    private var counter = 100
    private fun gameStart() {
        var count = 3000
        var tick = 30

        flow!!.removeAllViews()
        if (currentScore in 3..5) {
            currentblock = Constant.BLOCK_3
        } else if (currentScore in 6..8) {
            currentblock = Constant.BLOCK_4
            count = 2800
            tick = 28
        } else if (currentScore in 9..11) {
            currentblock = Constant.BLOCK_5
            currentlight = Constant.LIGHT_MEDIUM
            count = 2600
            tick = 26
        } else if (currentScore in 12..14) {
            currentblock = Constant.BLOCK_6
            count = 2400
            tick = 24
        } else if (currentScore in 15..17) {
            currentblock = Constant.BLOCK_7
            currentlight = Constant.LIGHT_HARD
            count = 2200
            tick = 22
        } else if (currentScore >= 18) {
            currentblock = Constant.BLOCK_8
            count = 2000
            tick = 20
        }
        val rand = Random()
        val n = rand.nextInt(Constant.colors.size)
        val nl = rand.nextInt(currentblock * currentblock)
        val color = Color.parseColor(Constant.colors[n])
        val blockWidth = (flow!!.width - currentblock * 2) / currentblock

        var Clcounter = 0
        for (i in 0 until currentblock) {
            for (j in 0 until currentblock) {
                val params = LayoutParam(blockWidth, blockWidth)
                val v = View(this)
                v.setBackgroundResource(R.drawable.border_view)
                val drawable = v.background as GradientDrawable

                v.tag = Clcounter == nl
                if (Clcounter == nl) {
                    drawable.setColor(lighter(color, currentlight))
                } else drawable.setColor(color)

                params.setMargins(1, 1, 1, 1)
                v.layoutParams = params
                flow!!.addView(v)
                v.setOnClickListener(this)
                Clcounter++
            }
        }

        viewTimer!!.progress = 100
        counter = 100
        timer = object : CountDownTimer(count.toLong(), tick.toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                //Log.d("CountdownTimer", "Remaining time: $counter")
                counter--
                viewTimer!!.progress = counter
            }

            override fun onFinish() {
                gameOver()
                viewTimer!!.progress = 0
            }
        }

        if (!isFirstimeTouch) {
            timer!!.start()
        } else isFirstimeTouch = false
    }

    override fun onClick(v: View) {
        if (v.tag != null) {
            if (!(v.tag as Boolean)) {
                if (timer != null) timer!!.cancel()
                gameOver()
            } else {
                if (mMediaPlayer != null) {
                    mMediaPlayer!!.stop()
                    mMediaPlayer!!.release()
                    mMediaPlayer = null
                }
                val mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.btn_sound)
                mMediaPlayer.start()
                currentScore++
                txtScore!!.text = currentScore.toString()
                if (timer != null) timer!!.cancel()
                gameStart()
            }
        }
    }

    companion object {
        fun lighter(color: Int, factor: Float): Int {
            val red = ((Color.red(color) * (1 - factor) / 255 + factor) * 255).toInt()
            val green = ((Color.green(color) * (1 - factor) / 255 + factor) * 255).toInt()
            val blue = ((Color.blue(color) * (1 - factor) / 255 + factor) * 255).toInt()
            return Color.argb(Color.alpha(color), red, green, blue)
        }
    }
}