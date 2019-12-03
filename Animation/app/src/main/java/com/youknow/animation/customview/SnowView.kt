package com.youknow.animation.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


private const val NUM_SNOWFLAKES = 150
private const val DELAY = 5L

class SnowView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var snowflakes: Array<SnowFlake?> = arrayOf()

    protected fun resize(width: Int, height: Int) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.setColor(Color.WHITE)
        paint.setStyle(Paint.Style.FILL)
        snowflakes = arrayOfNulls(NUM_SNOWFLAKES)
        for (i in 0 until NUM_SNOWFLAKES) {
            snowflakes[i] = SnowFlake.create(width, height, paint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w != oldw || h != oldh) {
            resize(w, h)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (snowFlake in snowflakes) {
            snowFlake?.draw(canvas)
        }
        handler.postDelayed(runnable, DELAY)
    }

    private val runnable = Runnable { invalidate() }
}