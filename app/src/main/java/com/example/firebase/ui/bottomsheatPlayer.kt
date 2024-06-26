package com.example.firebase.ui

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.navigation.fragment.navArgs
import com.example.firebase.databinding.PlayerBinding
import com.example.firebase.databinding.PlayerCountriesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.IOException

class bottomsheatPlayer:BottomSheetDialogFragment() {

    lateinit var mediaPlayer : MediaPlayer

    lateinit var binding: PlayerCountriesBinding
    lateinit var audioManager : AudioManager

    val args: PlayerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlayerCountriesBinding.inflate(inflater, container, false)
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
        audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        binding.seekBar.apply {
            max = maxVolume
            progress = curVolume
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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