package com.aaron.mediaplayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.media3.exoplayer.ExoPlayer
import com.aaron.mediaplayer.ui.theme.MediaPlayerTheme

class MainActivity : ComponentActivity() {

    private val vm: MainViewModel by viewModels()

    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        player = ExoPlayer.Builder(this).build()
        val playList = vm.playList

        setContent {
            MediaPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MediaPlayerScreen(player, playList)
                }
            }
        }
    }
}