package com.sisalma.movieticketapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.appActivity.Home
import com.sisalma.movieticketapp.usersign.SignInActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val setting = application.getSharedPreferences("app-setting", MODE_PRIVATE)
        var handler = Handler()

        if(setting.getString("username","").toString().isNotBlank() && setting.getString("password","").toString().isNotBlank()){
            var intent = Intent(this, Home::class.java)
            startActivity(intent)
            finishAffinity()
        }else{
            if(setting.getBoolean("oldUser",false)){
                var intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                handler.postDelayed({
                    var intent = Intent(this, OnboardingOneActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 500)
            }
        }
    }
}