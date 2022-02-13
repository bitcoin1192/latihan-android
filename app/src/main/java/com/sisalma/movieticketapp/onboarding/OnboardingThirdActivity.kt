package com.sisalma.movieticketapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.usersign.SignInActivity

class OnboardingThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_third)

        var next = findViewById<Button>(R.id.buttonTrue)

        next.setOnClickListener(){
            finishAffinity()
            var intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

}