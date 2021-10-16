package com.sisalma.movieticketapp

import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.view.View
import android.widget.*
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.security.Permission
import java.util.jar.Manifest
import java.util.logging.Logger

//I guess dexter hasn't added support for latest android api,
//error like "class doesn't have constructor" popup when trying
//to extend this activity with PermissionListener()
class photoUp_page : AppCompatActivity(){

    lateinit var database: DatabaseReference
    lateinit var storage: FirebaseStorage
    lateinit var storageRef: StorageReference
    lateinit var filepath:Uri

    val requst_image = 1
    var statusAdd:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photoUp_page)
        //Set database reference to latihan-mta/User firebase "https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/"
        database = FirebaseDatabase.getInstance().getReference("User")
        storageRef  = FirebaseStorage.getInstance().getReference("Photos")
        //I wish this variable can be found automatically
        var btnBack = findViewById<ImageView>(R.id.imageView4)
        var next = findViewById<Button>(R.id.buttonTrue)
        var btnAdd = findViewById<ImageView>(R.id.imageView5)

        next.setOnClickListener(){

        }
        btnBack.setOnClickListener(){
            //Move back to login activity
            finish()
        }
        btnAdd.setOnClickListener(){
            //If user is already adding image, make next button visible
            //and show new image selected by user
            //else, ask user to allow camera access
            if(statusAdd){
                statusAdd = false
                next.visibility = View.VISIBLE
            }else{
            //TODO: try to access camera folder for user selecting their profile pictures
            }
        }
    }
    private fun saveUsertoFirebase(dataUser: Users){
        database.child(dataUser.username).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(Users::class.java)
                if (user != null) {
                    Toast.makeText(this@photoUp_page, "Nama user "+ user.username + " tidak tersedia",Toast.LENGTH_LONG).show()
                }else{
                    // Update firebase to set new user then
                    database.child(dataUser.username).setValue(dataUser)
                    Toast.makeText(this@photoUp_page,"User berhasil dibuat", Toast.LENGTH_LONG).show()
                }
            }
            override fun onCancelled(dbErr: DatabaseError) {
                Toast.makeText(this@photoUp_page,dbErr.message,Toast.LENGTH_LONG).show()
            }
        })
    }
}