package com.example.smartalarm.colorGame.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import org.jetbrains.annotations.Nullable

class BorderedView : View {
    var color = Color.BLACK
    var border = Color.WHITE

    constructor(context: Context?) : super(context)
    constructor(context: Context?, @Nullable attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    public override fun onDraw(canvas: Canvas) {
        val paint = Paint()
        paint.color = color
        paint.style = Paint.Style.STROKE
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }
}