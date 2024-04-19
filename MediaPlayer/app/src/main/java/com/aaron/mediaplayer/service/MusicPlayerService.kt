package com.aaron.mediaplayer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.aaron.mediaplayer.R
import com.aaron.mediaplayer.ui.MainActivity

class MusicPlayerService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private val binder = MusicBinder()

    inner class MusicBinder: Binder() {
        fun getService(): MusicPlayerService = this@MusicPlayerService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.lose_yourself)
        mediaPlayer.isLooping = true
        createNotificationChannel()
        val notification = createNotification()
        startForeground(32, createNotification(), ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
        Build.VERSION_CODES.UPSIDE_DOWN_CAKE
        ServiceCompat.startForeground(
            this,
            32,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK,
        )
        Log.w("TEST", "[test] startForeground")
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }


    fun startMusic() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    fun stopMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Music Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun createNotification(): Notification {
        val pendingIntent: PendingIntent = PendingIntent.getActivities(this, 123, arrayOf(Intent(this, MainActivity::class.java)), PendingIntent.FLAG_IMMUTABLE, null)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Music Player")
            .setContentText("Playing music")
            .setSmallIcon(R.drawable.lose_yourself_album_cover)
            .setOngoing(true)
            .setContentIntent(pendingIntent)

        return notificationBuilder.build()
    }

    companion object {
        private const val CHANNEL_ID = "channel-id"
    }
}