package com.example.fcpushnotice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fcpushnotice.databinding.ActivityMainBinding
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            val token = it.result
            binding.firebaseTokenTv.text = token
        }
    }
}