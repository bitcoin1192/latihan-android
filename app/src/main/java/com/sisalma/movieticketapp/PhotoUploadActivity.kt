package com.sisalma.movieticketapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.sisalma.movieticketapp.appActivity.Home
import com.sisalma.movieticketapp.databinding.ActivityPhotouploadBinding

class PhotoUploadActivity() : AppCompatActivity(){

    lateinit var filepath:Uri

    val REQUEST_IMAGE_CAPTURE = 231
    var statusAdd = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settings = applicationContext.getSharedPreferences("app-setting", MODE_PRIVATE)
        val settingEditor = settings.edit()
        val name = settings.getString("username",null)?:""
        val pass = settings.getString("password",null)?:""
        val requestClean = settings.getBoolean("cleanAffinity",false)
        val user = intent.getParcelableExtra<dataUser>("data")

        val userObject = authenticatedUsers(applicationContext)
        userObject.userAuthenticate()

        var uiBind = ActivityPhotouploadBinding.inflate(layoutInflater)
        setContentView(uiBind.root)

        if(requestClean){
            uiBind.tvHello.text = ""
        }else{
            uiBind.tvHello.text = "Selamat datang, "+user!!.nama
        }

        val intentPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if(it != null) {
                filepath = it
                uiBind.btnSave.visibility = View.VISIBLE
                Glide.with(uiBind.ivProfile)
                    .load(filepath)
                    .into(uiBind.ivProfile)
            }
        }

        uiBind.btnSave.setOnClickListener{
            userObject.uploadImagetoFBStore(filepath)
            if(requestClean){
                finish()
            }else{
                val intent = Intent(this, Home::class.java)
                settingEditor.putBoolean("cleanAffinity",true)
                settingEditor.commit()
                finishAffinity()
                startActivity(intent)
            }
        }

        uiBind.btnHome.setOnClickListener(){
            finishAffinity()
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        uiBind.ivButtonBack.setOnClickListener(){
            finish()
        }

        uiBind.ivAdd.setOnClickListener(){
            //If user is already adding image, make next button visible
            //and show new image selected by user
            //else, ask user to allow camera access
            if(statusAdd){
                statusAdd = false

            }else{
                intentPhoto.launch("image/*")
                statusAdd = true
            }
        }
    }
}