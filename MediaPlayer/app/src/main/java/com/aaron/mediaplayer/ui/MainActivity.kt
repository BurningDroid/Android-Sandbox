package com.aaron.mediaplayer.ui

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.media3.exoplayer.ExoPlayer
import com.aaron.mediaplayer.service.MusicPlayerService
import com.aaron.mediaplayer.ui.theme.MediaPlayerTheme

class MainActivity : ComponentActivity() {

    private val vm: MainViewModel by viewModels()

    private lateinit var player: ExoPlayer

    private var musicService: MusicPlayerService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicPlayerService.MusicBinder
            musicService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        player = ExoPlayer.Builder(this).build()
        val playList = vm.playList

        requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1000)
        setContent {
            MediaPlayerTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    MediaPlayerScreen(player, playList)
//                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        startService()
                    }) {
                        Text(text = "start service")
                    }

                    Button(onClick = {
                        stopService()
                    }) {
                        Text(text = "stop service")
                    }

                    Button(onClick = {
                        musicService?.startMusic()
                    }) {
                        Text(text = "play")
                    }
                    Button(onClick = { musicService?.stopMusic() }) {
                        Text(text = "stop")
                    }
                }
            }
        }
    }

    private fun startService() {
        val intent = Intent(this, MusicPlayerService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    private fun stopService() {
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}