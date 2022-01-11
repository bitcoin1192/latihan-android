package com.sisalma.movieticketapp.usersign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.sisalma.movieticketapp.GuestUser
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
        val guest = GuestUser(applicationContext)
        val userCheckCoroutine = CoroutineScope(Dispatchers.Main)

        binding.buttonTrue.setOnClickListener {
            Log.i("userInput", "${inputUsername} and ${inputPassword}")
            guest.userAuthenticate(inputUsername.toString(),inputPassword.toString())
            userCheckCoroutine.launch {
                var result = async {guest.testAuthorizeUser()}
                if(result.await()){
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