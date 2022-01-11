package com.sisalma.movieticketapp.usersign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.*
import com.sisalma.movieticketapp.*
import com.sisalma.movieticketapp.databinding.ActivitySignUpBinding


class signUpActivity : AppCompatActivity() {
    private val guestUser = GuestUser(applicationContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uiBind = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(uiBind.root)
        uiBind.buttonTrue.setOnClickListener(){
            var dataUser = dataUser(uiBind.inputUser.text.toString(),
                uiBind.inputPass.text.toString(),
                uiBind.inputMail.text.toString(),
                uiBind.inputNama.text.toString(),
                0,"")

            if (dataUser.email!!.isEmpty() or dataUser.username.isEmpty() or
                dataUser.nama!!.isEmpty() or dataUser.password!!.isEmpty()){
                Toast.makeText(this,"Isi semua kolom diatas untuk melanjutkan pendaftaran akun", Toast.LENGTH_LONG).show()
            }else{
                guestUser.daftarBaru(dataUser)
                Toast.makeText(this,"User berhasil dibuat", Toast.LENGTH_LONG).show()
                val intent = Intent(this, photoUploadActivity::class.java).putExtra("data",dataUser)
                startActivity(intent)
            }
        }

        uiBind.imageView4.setOnClickListener(){
            //Move back to login activity
            finish()
        }
    }
}