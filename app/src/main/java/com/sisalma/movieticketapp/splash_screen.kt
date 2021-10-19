package com.sisalma.movieticketapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sisalma.movieticketapp.onboarding.OnboardingOneActivity

class splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var handler = Handler()
        handler.postDelayed({
            var intent = Intent(this@splash_screen, OnboardingOneActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}