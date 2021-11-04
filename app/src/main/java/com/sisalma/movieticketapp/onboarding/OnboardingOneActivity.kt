package com.sisalma.movieticketapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.usersign.activity_sign_in

class OnboardingOneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        var next = findViewById<Button>(R.id.buttonTrue)
        var skip = findViewById<Button>(R.id.button_false)

        next.setOnClickListener(){
            var intent = Intent(this, OnboardingTwoActivity::class.java)
            startActivity(intent)
        }

        skip.setOnClickListener(){
            var intent = Intent(this, activity_sign_in::class.java)
            startActivity(intent)
            finish()
        }
    }

}