package com.yoonho.groupnoti

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var notiId = 1

    private val notiMgr: NotificationManagerCompat by lazy { NotificationManagerCompat.from(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createChannel()

        btnClick.setOnClickListener {
            startNoti()
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "channel name", NotificationManager.IMPORTANCE_LOW)
            notiMgr.createNotificationChannel(channel)

        }
    }

    private fun startNoti() {
        val noti = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("title [$notiId]")
                .setContentText("body [$notiId]")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setGroup(GROUP_KEY_WORK_EMAIL)
                .build()

        val summaryNoti = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("summary")
                .setContentText("body")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setGroup(GROUP_KEY_WORK_EMAIL)
                .setGroupSummary(true)
                .build()

        notiMgr.notify(notiId++, noti)
        notiMgr.notify(0, summaryNoti)
    }

    companion object {
        private const val CHANNEL_ID = "channel id"
        private const val GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL"
    }
}
