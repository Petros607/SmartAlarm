package com.example.smartalarm.Guide.Model.Service

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class AudioController {
    private var mediaPlayer: MediaPlayer? = null

    fun startAudio(context: Context, ringtoneUriString: String?) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(context, Uri.parse(ringtoneUriString))
            prepare()
            isLooping = true
            start()
        }
    }

    fun stopAudio() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.release()
            }
        }
    }
}
