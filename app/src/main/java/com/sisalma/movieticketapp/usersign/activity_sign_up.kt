package com.sisalma.movieticketapp.usersign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.*
import com.sisalma.movieticketapp.*
import com.sisalma.movieticketapp.R


class activity_sign_up : AppCompatActivity() {
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //Set database reference to latihan-mta/User firebase
        database = FirebaseDatabase.getInstance("https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
        //I wish this variable can be found automatically
        var btnBack = findViewById<ImageView>(R.id.imageView4)
        var next = findViewById<Button>(R.id.buttonTrue)
        var user = findViewById<EditText>(R.id.inputUser)
        var pass = findViewById<EditText>(R.id.inputPass)
        var name = findViewById<EditText>(R.id.inputNama)
        var mail = findViewById<EditText>(R.id.inputMail)

        next.setOnClickListener(){
            var dataUser = dataUser(user.text.toString(),pass.text.toString(),mail.text.toString()
                ,name.text.toString(),"0","")
            var user = guestUser()
            if (dataUser.email!!.isEmpty() or dataUser.username.isEmpty() or dataUser.nama!!.isEmpty()
            or dataUser.password!!.isEmpty()){
                Toast.makeText(this,"Salah satu kolom diatas belum terisi", Toast.LENGTH_LONG).show()
            }else{
                user.daftarBaru(dataUser)
            }

        }
        btnBack.setOnClickListener(){
            //Move back to login activity
            finish()
        }
    }
    private fun saveUsertoFirebase(dataUser: Users){
        database.child(dataUser.username!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(Users::class.java)
                if (user != null) {
                    Toast.makeText(this@activity_sign_up, "Nama user "+ user.username + " tidak tersedia",Toast.LENGTH_LONG).show()
                }else{
                    // Update firebase to set new user then
                    database.child(dataUser.username!!).setValue(dataUser)
                    Toast.makeText(this@activity_sign_up,"User berhasil dibuat", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@activity_sign_up, photoUp_page::class.java).putExtra("data",dataUser.nama)
                    startActivity(intent)
                }
            }
            override fun onCancelled(dbErr: DatabaseError) {
                Toast.makeText(this@activity_sign_up,dbErr.message,Toast.LENGTH_LONG).show()
            }
        })
    }
}