package com.example.smartalarm.colorGame


import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.smartalarm.R


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("SPLASH", "Happened")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val h = Handler()
        h.postDelayed({
            // TODO Auto-generated method stub
            YoYo.with(Techniques.FadeOut).duration(700).withListener(object : AnimatorListener {
                override fun onAnimationStart(arg0: Animator) {
                    // TODO Auto-generated method stub
                }

                override fun onAnimationRepeat(arg0: Animator) {
                    // TODO Auto-generated method stub
                }

                override fun onAnimationEnd(arg0: Animator) {
                    // TODO Auto-generated method stub
                    val intent = Intent(this@SplashActivity, GameMainActivity::class.java)
                    startActivity(intent)
                    finish()
                    //overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }

                override fun onAnimationCancel(arg0: Animator) {
                    // TODO Auto-generated method stub
                }
            }).playOn(findViewById(R.id.logo))
        }, 2000)
    }
}