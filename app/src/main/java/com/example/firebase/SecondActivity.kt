package com.example.firebase

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.adapter.RadioAdapter
import com.example.firebase.data.Radio
import com.example.firebase.databinding.ActivityMainBinding
import com.example.firebase.databinding.SecontActivityBinding
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.IOException

class SecondActivity: AppCompatActivity() {
    lateinit var binding: SecontActivityBinding
    private val repository = Repository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SecontActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}