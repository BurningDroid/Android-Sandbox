package com.youknow.animation.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.youknow.animation.R
import kotlinx.android.synthetic.main.activity_snowing.*
import kotlin.random.Random

private const val SNOWING_MESSAGE_ID = 10

class SnowingActivity : AppCompatActivity(), Handler.Callback {

    private var isSnowing: Boolean = true
    private val delayedSnowing: Handler = Handler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snowing)

        delayedSnowing.sendEmptyMessageDelayed(SNOWING_MESSAGE_ID, 100)
    }

    private fun snowing() {
        val snowObj = makeSnowObject()
        container.addView(snowObj)

        val startPointX = Random.nextFloat() * container.width
        val endPointX = Random.nextFloat() * container.width
        val moverX = ObjectAnimator.ofFloat(snowObj, View.TRANSLATION_X, startPointX, endPointX)

        val snowHeight = snowObj.measuredHeight * snowObj.scaleY
        val startPointY = -snowHeight
        val endPointY = (container.height + snowHeight)
        val moverY = ObjectAnimator.ofFloat(snowObj, View.TRANSLATION_Y, startPointY, endPointY).apply {
            interpolator = AccelerateInterpolator(1f)
        }

        val set = AnimatorSet().apply {
            playTogether(moverX, moverY)
            duration = (Math.random() * 3000 + 3000).toLong()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    container.removeView(snowObj)
                }
            })
        }
        set.start()
    }

    private fun makeSnowObject() = AppCompatImageView(this).apply {
        setImageResource(R.drawable.ic_snow)
        scaleX = Math.random().toFloat() * 0.3f + .2f
        scaleY = scaleX
        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    }

    override fun handleMessage(msg: Message): Boolean {
        if (msg.what == SNOWING_MESSAGE_ID && isSnowing) {
            snowing()
            delayedSnowing.sendEmptyMessageDelayed(
                SNOWING_MESSAGE_ID,
                (Random.nextFloat() * 1 + 1000).toLong()
            )
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        isSnowing = false
        delayedSnowing.removeCallbacksAndMessages(null)
    }
}
