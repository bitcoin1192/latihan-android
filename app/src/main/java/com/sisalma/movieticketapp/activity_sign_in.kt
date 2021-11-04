package com.sisalma.movieticketapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import java.util.*
import java.util.logging.Logger


class activity_sign_in : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val next = findViewById<Button>(R.id.buttonTrue)
        val btnDaftar = findViewById<Button>(R.id.buttonFalse)
        var inputUser = findViewById<EditText>(R.id.inputUser)
        var inputPass = findViewById<EditText>(R.id.inputPass)
        var authUser = authenticatedUsers()

        next.setOnClickListener(){
            authUser.userAuthenticate(inputUser.text.toString(),inputPass.text.toString())
            //Unreliable because of async behavior of firebase library,
            if(authUser.isAuthFailed()){
                Toast.makeText(this@activity_sign_in,"Authentication Failed, Try Again later", Toast.LENGTH_LONG).show()
            }else {
                //val intent = Intent(this, home::class.java)
                Toast.makeText(this@activity_sign_in,"Authentication Success, Go to Home", Toast.LENGTH_LONG).show()
            }
        }
        btnDaftar.setOnClickListener(){
            val intent = Intent(this, activity_sign_up::class.java)
            startActivity(intent)
        }

    }

    private fun testAuthorizeUser(authenticatedUsers: authenticatedUsers): Int{
        while (!authenticatedUsers.isAuthenticated()){
            if(authenticatedUsers.isAuthFailed()){
                return 1
            }
        }
        return 0
    }
}