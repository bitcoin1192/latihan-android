package com.sisalma.movieticketapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.LogPrinter
import android.view.View
import android.widget.*
import androidx.core.app.NotificationCompat
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
import java.util.*
import java.util.jar.Manifest
import java.util.logging.Logger
import android.app.NotificationManager

import android.app.NotificationChannel
import android.content.Context
import android.graphics.Path
import android.os.Build
import android.os.Parcelable
import android.service.notification.NotificationListenerService
import androidx.core.app.NotificationManagerCompat
import java.util.prefs.Preferences


//I guess dexter hasn't added support for latest android api,
//error like "class doesn't have constructor" popup when trying
//to extend this activity with PermissionListener()
class photoUp_page : AppCompatActivity(), PermissionListener{

    lateinit var database: DatabaseReference
    lateinit var storage: FirebaseStorage
    lateinit var storageRef: StorageReference
    lateinit var User:Users

    lateinit var filepath:Uri

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean = false

    val preferences = Preferences(this)
    val user = intent.getParcelableExtra<Parcelable>("data")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photoUp_page)
        //Set database reference to latihan-mta/User firebase "https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/"
        database = FirebaseDatabase.getInstance().getReference("User")
        storageRef  = FirebaseStorage.getInstance().getReference("Photos")
        //I wish this variable can be found automatically
        var btnBack = findViewById<ImageView>(R.id.imageView4)
        var next = findViewById<Button>(R.id.buttonTrue)
        var skip = findViewById<Button>(R.id.buttonFalse)
        var btnAdd = findViewById<ImageView>(R.id.imageView5)
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "1",
                "General Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "We will notify you when long living operation is done"
            mNotificationManager.createNotificationChannel(channel)
        }
        next.setOnClickListener(){
            val notifBuild = NotificationCompat.Builder(this, "3")
                .setContentTitle("Image Uploading")
                .setContentText("Uploading in progress")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
            with(NotificationManagerCompat.from(this)) {
                notify(0, notifBuild.build())
            }
            var path = storageRef.child("Photos/"+UUID.randomUUID().toString())
            path.putFile(filepath)
                .addOnSuccessListener {
                    Toast.makeText(this,"Upload berhasil", Toast.LENGTH_LONG).show()
                    path.downloadUrl.addOnSuccessListener {
                        savetoFirebase(path.path)
                    }
                    val intent = Intent(this,home::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
                .addOnFailureListener {
                    e ->
                    val notifBuild = NotificationCompat.Builder(this, "3")
                        .setContentTitle("Image Uploading")
                        .setContentText("Something unexpected happen, Please try clicking the next button again!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                    with(NotificationManagerCompat.from(this)) {
                        notify(1, notifBuild.build())
                    }
                }
                .addOnProgressListener {

                }
        }
        skip.setOnClickListener(){
            finishAffinity()
            val intent = Intent(this@photoUp_page, home::class.java)
            startActivity(intent)
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
            //TODO: try to access camera folder and prompt user to select their new profile pictures
                Dexter.withContext(this)
                    .withPermission(android.Manifest.permission.CAMERA)
                    .withListener(this)
                    .check()
            }
        }
    }
    private fun savetoFirebase(Url: String){
        database.child(user.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Mentok cara akses dataUsers dari activity sebelumnya
                //WTH is Preferences class anyway ???
                user.url = Url
                database.child(user?.username!!).setValue(user)
                if (user != null) {
                    Toast.makeText(this@photoUp_page, "Nama user "+ user.username + " tidak tersedia",Toast.LENGTH_LONG).show()
                }else{
                    // Update firebase to set new user then

                    Toast.makeText(this@photoUp_page,"User berhasil dibuat", Toast.LENGTH_LONG).show()
                }
            }
            override fun onCancelled(dbErr: DatabaseError) {
                Toast.makeText(this@photoUp_page,dbErr.message,Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        TODO("Not yet implemented")
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            takeImageIntent -> takeImageIntent.resolveActivity(packageManager).also {
                startActivityForResult(takeImageIntent, REQUEST_IMAGE_CAPTURE)
        }
        }
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        TODO("Not yet implemented")
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode.equals(REQUEST_IMAGE_CAPTURE) and resultCode.equals(Activity.RESULT_OK)){
            var imageBit = data?.extras.?get("data") as Bitmap
            statusAdd = true

            filepath = data.data

        }
    }
}