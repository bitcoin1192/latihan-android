package com.sisalma.movieticketapp.usersign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.databinding.ActivitySignInBinding
import com.sisalma.movieticketapp.appActivity.home
import kotlinx.coroutines.*
import java.util.*


class signInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding =   ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var inputUsername = binding.inputUser.text
        var inputPassword = binding.inputPass.text
        var authUser = authenticatedUsers()
        val userCheckCoroutine = CoroutineScope(Dispatchers.Main)

        binding.buttonTrue.setOnClickListener {
            authUser.userAuthenticate(inputUsername.toString(),inputPassword.toString())
            userCheckCoroutine.launch {
                var result = async {authUser.testAuthorizeUser(authUser)}
                if(result.await()){
                    val settingEditor = applicationContext.getSharedPreferences("app-setting", MODE_PRIVATE).edit()
                    settingEditor.putString("username",inputUsername.toString())
                    settingEditor.putString("password",inputPassword.toString())
                    settingEditor.putString("oldUser","true")
                    settingEditor.commit()
                    finishAffinity()
                    Toast.makeText(this@signInActivity,"Authentication Success, goto home", Toast.LENGTH_SHORT).show()
                    intent = Intent(this@signInActivity,home::class.java)
                    startActivity(intent)
                }else {
                    Toast.makeText(this@signInActivity,"Authentication Fail, please try again", Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.buttonFalse.setOnClickListener(){
            val intent = Intent(this, signUpActivity::class.java)
            startActivity(intent)
        }

    }
}