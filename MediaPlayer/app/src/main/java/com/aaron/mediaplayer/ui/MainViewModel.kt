package com.aaron.mediaplayer.ui

import androidx.lifecycle.ViewModel
import com.aaron.mediaplayer.music.Music
import com.aaron.mediaplayer.R

class MainViewModel : ViewModel() {

    val playList: List<Music> = listOf(
        Music(
            name = "Lose Yourself",
            artist = "Eminem",
            cover = R.drawable.lose_yourself_album_cover,
            music = R.raw.lose_yourself
        ),
        Music(
            name = "Everyday Normal Guy 2",
            artist = "Jon Lajoie",
            cover = R.drawable.everyday_normal_guy_2_album_cover,
            music = R.raw.everyday_normal_guy_2
        ),
        Music(
            name = "Crazy",
            artist = "Gnarls Barkley",
            cover = R.drawable.crazy_album_cover,
            music = R.raw.crazy
        ),
    )
}