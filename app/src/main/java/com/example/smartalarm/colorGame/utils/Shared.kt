package com.example.smartalarm.colorGame.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.graphics.Point
import android.graphics.Typeface
import android.os.Build
import android.view.WindowManager


object Shared {
    private var instance: ContextWrapper? = null
    private var pref: SharedPreferences? = null
    var appfont: Typeface? = null
    var appfontBold: Typeface? = null
    var appfontThin: Typeface? = null
    var appfontLight: Typeface? = null
    fun initialize(base: Context?) {
        instance = ContextWrapper(base)
        pref = instance!!.getSharedPreferences("com.yondev.colorforfun", Context.MODE_PRIVATE)
        appfont = Typeface.createFromAsset(instance!!.assets, "fonts/SourceSansPro-Regular.ttf")
        appfontBold = Typeface.createFromAsset(instance!!.assets, "fonts/SourceSansPro-Bold.ttf")
        appfontLight = Typeface.createFromAsset(instance!!.assets, "fonts/SourceSansPro-Light.ttf")
    }

    fun write(key: String?, value: String?) {
        val editor = pref!!.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun read(key: String?): String? {
        return read(key, null)
    }

    fun read(key: String?, defValue: String?): String? {
        return pref!!.getString(key, defValue)
    }

    fun clear() {
        val editor = pref!!.edit()
        editor.clear()
        editor.commit()
    }

    fun clear(key: String?) {
        val editor = pref!!.edit()
        editor.remove(key)
        editor.commit()
    }

    val context: Context
        get() = instance!!.baseContext

    fun DipToInt(value: Int): Int {
        return (instance!!.resources.displayMetrics.density * value).toInt()
    }

    val displayHeight: Int
        get() {
            val wm = instance!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val version = Build.VERSION.SDK_INT
            return if (version >= 13) {
                val size = Point()
                display.getSize(size)
                size.y
            } else {
                display.height
            }
        }
    val displayWidth: Int
        get() {
            val wm = instance!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val version = Build.VERSION.SDK_INT
            return if (version >= 13) {
                val size = Point()
                display.getSize(size)
                size.x
            } else {
                display.width
            }
        }
}