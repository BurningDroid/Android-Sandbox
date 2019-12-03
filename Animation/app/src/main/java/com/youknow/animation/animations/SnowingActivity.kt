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

    private var isSnowing: Boolean = true
    private val delayedSnowing: Handler = Handler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snowing)

        delayedSnowing.sendEmptyMessageDelayed(SNOWING_MESSAGE_ID, 100)
    }

private fun snowing() {

    // 눈 객체를 생성하여 container에 추가 (container는 해당 액티비티의 전체 layout입니다.)
    val snowObj = makeSnowObject()
    container.addView(snowObj)

    // 눈 객체의 높이
    val snowHeight = snowObj.measuredHeight * snowObj.scaleY

    // 눈 객체가 하늘에서 내릴 때 랜덤한 위치에서 내리기 시작하여, 랜덤한 위치로 떨어지기 위해 시작/종료 지점의 X 좌표 계산
    val startPoint = Random.nextFloat() * container.width
    val endPoint = Random.nextFloat() * container.width

    // 눈 객체의 시작 위치와 종료 위치의 X 좌표
    val moverX = ObjectAnimator.ofFloat(snowObj, View.TRANSLATION_X, startPoint, endPoint)

    // 눈 객체의 시작 위치와 종료 위치의 Y 좌표
    // 화면의 최상단보다 좀더 위쪽에서 시작하도록 하기 위해 0이 아닌 -snowHeight로 지정
    // 화면의 하단으로 사라지게끔 하기 위해 종료 위치를 container height + snowHeight로 지정
    val moverY = ObjectAnimator.ofFloat(snowObj, View.TRANSLATION_Y, -snowHeight, (container.height + snowHeight))

    // 눈이 떨어지는 속도 조절
    moverY.interpolator = AccelerateInterpolator(1f)

    // 2개의 애니메이션을 하나로 묶어서 처리하기 위해 AnimatorSet 사용
    val set = AnimatorSet().apply {
        playTogether(moverX, moverY)
        duration = (Math.random() * 3000 + 3000).toLong()
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                // 애니메이션 종료 뒤 해당 눈 객체 제거
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
            delayedSnowing.sendEmptyMessageDelayed(SNOWING_MESSAGE_ID, (Random.nextFloat() * 1 + 1000).toLong())
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        isSnowing = false
        delayedSnowing.removeCallbacksAndMessages(null)
    }
}
