package com.sisalma.movieticketapp.usersign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.sisalma.movieticketapp.*
import com.sisalma.movieticketapp.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {
    private lateinit var guestUser: GuestUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        guestUser = GuestUser(applicationContext)
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
                val intent = Intent(this, PhotoUploadActivity::class.java).putExtra("data",dataUser)
                startActivity(intent)
            }
        }

        uiBind.imageView4.setOnClickListener(){
            //Move back to login activity
            finish()
        }
    }
}