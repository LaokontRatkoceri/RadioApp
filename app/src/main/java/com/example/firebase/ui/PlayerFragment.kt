package com.example.firebase.ui

import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.navArgs
import com.example.firebase.adapter.RadioAdapter
import com.example.firebase.data.Radio
import com.example.firebase.databinding.FragmentHomeBinding
import com.example.firebase.databinding.PlayerBinding
import com.example.firebase.ui.PlayerFragmentArgs
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class PlayerFragment: BottomSheetDialogFragment() {
    lateinit var mediaPlayer: MediaPlayer


    private lateinit var binding: PlayerBinding
    private lateinit var audioManager: AudioManager
    private lateinit var player: ExoPlayer

    val args: PlayerFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameRadio.text = args.radioId
        val audioUrl = args.urlId


        mediaPlayer = MediaPlayer()



        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
        }

        binding.playButton.setOnClickListener {

            if (binding.playButton.text == "Pause") {
                pauseAudio()
            } else {
                playAudio(audioUrl)
            }
        }

        updateButtonVisibility(mediaPlayer.isPlaying)

        setupVolumeControl()

    }

    private fun playAudio(audioUrl: String) {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(audioUrl)
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
                updateButtonVisibility(true)
            }
            mediaPlayer.prepareAsync()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun pauseAudio() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            updateButtonVisibility(false)
        }
    }

    private fun updateButtonVisibility(isPlaying: Boolean) {
        if (isPlaying) {
            binding.playButton.text = "Pause"
        } else {
            binding.playButton.text = "Play"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }

    private fun setupVolumeControl() {
        audioManager = requireContext().getSystemService(AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        binding.seekBar.apply {
            max = maxVolume
            progress = curVolume
            setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                    }
                }
            })
        }
    }
}