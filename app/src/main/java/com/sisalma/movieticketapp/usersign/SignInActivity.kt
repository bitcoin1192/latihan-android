package com.sisalma.movieticketapp.usersign

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.database.*
import com.sisalma.movieticketapp.GuestUser
import com.sisalma.movieticketapp.databinding.ActivitySignInBinding
import com.sisalma.movieticketapp.appActivity.Home
import kotlinx.coroutines.*
import java.util.*


class SignInActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding =   ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val guest = GuestUser(applicationContext)
        val userCheckCoroutine = CoroutineScope(Dispatchers.Main)

        binding.buttonTrue.setOnClickListener {
            var inputUsername = binding.inputUser.text
            var inputPassword = binding.inputPass.text
            Log.i("userInput", "${inputUsername} and ${inputPassword}")
            guest.userAuthenticate(inputUsername.toString(),inputPassword.toString())
            userCheckCoroutine.launch {
                var result = async {guest.testAuthorizeUser()}
                if(result.await()){
                    finishAffinity()
                    Toast.makeText(this@SignInActivity,"Authentication Success, goto home", Toast.LENGTH_SHORT).show()
                    intent = Intent(this@SignInActivity,Home::class.java)
                    startActivity(intent)
                }else {
                    Toast.makeText(this@SignInActivity,"Authentication Fail, please try again", Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.buttonFalse.setOnClickListener(){
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}