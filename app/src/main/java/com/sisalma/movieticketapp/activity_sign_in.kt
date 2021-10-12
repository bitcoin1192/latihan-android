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
import java.util.logging.Logger


class activity_sign_in : AppCompatActivity() {
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        database = FirebaseDatabase.getInstance("https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
        var next = findViewById<Button>(R.id.buttonTrue)
        var btnDaftar = findViewById<Button>(R.id.buttonFalse)
        var user = findViewById<EditText>(R.id.inputUser)
        var pass = findViewById<EditText>(R.id.inputPass)

        next.setOnClickListener(){
            var textUser = user.text.toString()
            var textPass = pass.text.toString()
            if(textUser.isEmpty() or textPass.isEmpty()){
                Toast.makeText(this,"Username atau password belum terisi", Toast.LENGTH_LONG).show()
            }else{
                checkLoginData(textUser,textPass)
            }
        }
        btnDaftar.setOnClickListener(){
            val intent = Intent(this, activity_sign_up::class.java)
            startActivity(intent)
        }

    }

    private fun checkLoginData(username:String, password:String):Int{
        database.child(username).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(Users::class.java)
                if (user != null) {
                    if(password == user.password.toString()){
                        val intent = Intent(this@activity_sign_in, activity_sign_up::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@activity_sign_in,"Password Salah",Toast.LENGTH_LONG).show()
                    }
                }else{

                    Toast.makeText(this@activity_sign_in,"User Tidak ditemukan",Toast.LENGTH_LONG).show()
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@activity_sign_in,p0.message,Toast.LENGTH_LONG).show()
            }
        })
        return 1
    }
}