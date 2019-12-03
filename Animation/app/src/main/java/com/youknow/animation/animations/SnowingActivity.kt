package com.youknow.animation.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.youknow.animation.R
import kotlinx.android.synthetic.main.activity_snowing.*
import kotlin.random.Random

private const val SNOWING_MESSAGE_ID = 10

class SnowingActivity : AppCompatActivity(), Handler.Callback {

    private val TAG = SnowingActivity::class.java.simpleName

    private val delayedSnowing: Handler = Handler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snowing)

        delayedSnowing.sendEmptyMessageDelayed(SNOWING_MESSAGE_ID, 100)
    }

    private fun snowing() {
        val screenHeight = container.height
        val screenWidth = container.width

        val snowObj = makeSnowObject(screenWidth)
        container.addView(snowObj)

        val snowHeight = snowObj.measuredHeight * snowObj.scaleY

        val startPoint = Random.nextFloat() * screenWidth
        val endPoint = Random.nextFloat() * screenWidth

        val moverX = ObjectAnimator.ofFloat(snowObj, View.TRANSLATION_X, startPoint, endPoint)
        val moverY = ObjectAnimator.ofFloat(snowObj, View.TRANSLATION_Y, -snowHeight, (screenHeight - snowHeight))
        moverY.interpolator = AccelerateInterpolator(1f)

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

    private fun makeSnowObject(screenWidth: Int) = AppCompatImageView(this).apply {
        setImageResource(R.drawable.ic_snow)
        scaleX = Math.random().toFloat() * 0.3f + .2f
        scaleY = scaleX
        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        translationX = Math.random().toFloat() * screenWidth - (measuredWidth * scaleX) / 2
    }

    override fun handleMessage(msg: Message): Boolean {
        if (msg.what == SNOWING_MESSAGE_ID) {
            snowing()
        }

        delayedSnowing.sendEmptyMessageDelayed(SNOWING_MESSAGE_ID, (Random.nextFloat() * 1 + 1000).toLong())
        return true
    }
}
