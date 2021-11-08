package com.sisalma.movieticketapp.usersign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.database.*
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.databinding.ActivitySignInBinding
import com.sisalma.movieticketapp.home.home
import kotlinx.coroutines.*
import java.util.*


class activity_sign_in : AppCompatActivity() {
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
                var result = async {testAuthorizeUser(authUser)}
                if(result.await()){
                    //finishAffinity()
                    Toast.makeText(this@activity_sign_in,"Authentication Success, goto home", Toast.LENGTH_SHORT).show()
                    intent = Intent(this@activity_sign_in,home::class.java)
                    startActivity(intent)
                }else {
                    Toast.makeText(this@activity_sign_in,"Authentication Fail, please try again", Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.buttonFalse.setOnClickListener(){
            val intent = Intent(this, activity_sign_up::class.java)
            startActivity(intent)
        }

    }

    suspend fun testAuthorizeUser(authenticatedUsers: authenticatedUsers): Boolean{
        var counter = 0
        while (counter <= 4){
            if(authenticatedUsers.isAuthenticated()){
                Log.i("testAuthorize", "User found in try #$counter")
                return true
            }else if (authenticatedUsers.isAuthFailed()){
                Log.i("testAuthorize", "User or password not matching")
                return false
            }
            delay(500)
            counter++
        }
        Log.i("testAuthorize", "Counter exceed limits")
        return false
    }
}