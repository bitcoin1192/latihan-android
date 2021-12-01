package com.sisalma.movieticketapp.appActivity.editProfile

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.databinding.ActivityEditProfileBinding

class editProfileActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var uiBind = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(uiBind.root)
        val settings = applicationContext.getSharedPreferences("app-setting", MODE_PRIVATE)
        val name = settings.getString("username",null)?:""
        val pass = settings.getString("password",null)?:""

        val authUser = authenticatedUsers()
        authUser.userAuthenticate(name,pass)

        Log.i("editProfile", "It's alive")

        uiBind.btnHome.setOnClickListener {
            authUser.updateUserData(uiBind.etNama.text.toString(),
                uiBind.etEmail.text.toString(),
                uiBind.etPassword.text.toString(),null,null)

            val editSettings = settings.edit()
            editSettings.putString("password",uiBind.etPassword.text.toString()).apply()
            Toast.makeText(this,"Your profile has been change",Toast.LENGTH_SHORT).show()
            finish()
        }

        uiBind.ivBackButton.setOnClickListener {
            finish()
        }

    }
}