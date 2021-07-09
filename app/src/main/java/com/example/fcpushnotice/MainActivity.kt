package com.example.fcpushnotice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fcpushnotice.databinding.ActivityMainBinding
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        updateResult()
    }

    private fun init(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            val token = it.result
            Log.d("gawn2103","firebase Token ====> ${it.result}")
            binding.firebaseTokenTv.text = token
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        updateResult(true)
    }

    private fun updateResult(isNewIntent:Boolean = false){
        binding.resultTv.text = (intent.getStringExtra("notificationType") ?:"앱 런처") +
        if (isNewIntent){
            "(으)로 갱신했습니다."
        }else{
            "(으)로 실했습니다."
        }
    }
}