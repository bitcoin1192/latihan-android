package com.sisalma.movieticketapp.appActivity.editProfile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.databinding.ActivityEditProfileBinding
import com.sisalma.movieticketapp.photoUploadActivity

class editProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var uiBind = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(uiBind.root)
        val settings = applicationContext.getSharedPreferences("app-setting", MODE_PRIVATE)

        val authUser = authenticatedUsers(applicationContext)
        authUser.userAuthenticate()

        Log.i("editProfile", "It's alive")

        uiBind.btnHome.setOnClickListener {
            authUser.updateUserData(
                uiBind.etNama.text.toString(),
                uiBind.etEmail.text.toString(),
                uiBind.etPassword.text.toString(), null, null
            )
            Toast.makeText(this, "Your profile has been change", Toast.LENGTH_SHORT).show()
            finish()
        }

        uiBind.ivBackButton.setOnClickListener {
            finish()
        }

        uiBind.profilePictureEdit.setOnClickListener {
            val intent = Intent(this, photoUploadActivity::class.java).putExtra(
                "data",
                authUser.getUserData()
            )
            startActivity(intent)
        }

    }
}