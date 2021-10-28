package com.sisalma.movieticketapp

import android.graphics.Bitmap
import android.widget.Toast
import com.google.firebase.database.*

abstract class Users {
    lateinit var username: String
    lateinit var nama: String
    abstract fun getPhotoLink(): String?
}

abstract class readOnly: Users() {
    abstract fun daftarBaru(dataUser: dataUser)
}
abstract class readWrite: Users(){
    abstract fun userAuthenticate(username: String, password: String)
    abstract fun userDeauthenticate(): String
    abstract fun getUserData():dataUser
    abstract fun updateUserData(nama:String?, email: String?, password: String?)
    abstract fun setProfilePic()
    abstract fun getTicketHistory():ArrayList<Movie>
}
class authenticatedUsers(database: DatabaseReference):readWrite(){
    var authenticated = 0
    lateinit var user: dataUser
    val database = FirebaseDatabase.getInstance("https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")

    override fun userDeauthenticate(): String{
        TODO("Local Deauth by ending authenticated user lifecycle")
    }

    override fun setProfilePic() {
        TODO("Not yet implemented")
    }

    override fun getUserData():dataUser {
        //Check for authenticated flags
        if(authenticated.equals(1)){
            return user
        }
    }

    override fun updateUserData(nama:String?, email: String?, password: String?) {
        if(authenticated.equals(1)){
            database.child(user.username).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Update firebase to set new user then
                    if(!email.isNullOrEmpty()){
                        user.email = email
                    }
                    if(!password.isNullOrEmpty()){
                        user.password = password
                    }
                    if(!nama.isNullOrEmpty()){
                        user.nama = nama
                    }
                    database.child(user!!.username).setValue(user)
                }
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    override fun getTicketHistory(): ArrayList<Movie> {
        TODO("Not yet implemented")
    }

    override fun getPhotoLink(): String? {
        TODO("Not yet implemented")
    }

    override fun userAuthenticate(username: String, password: String){
        if (username.isNullOrEmpty() or password.isNullOrEmpty()){
            authenticated = 0
        }else{
            database.child(username).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var data = dataSnapshot.getValue(dataUser::class.java)
                    //On username found, check flags and assign user with data
                    if (data != null) {
                        if (password == data.password){
                            authenticated = 1
                            user = data
                        }else{
                            authenticated = 0
                        }
                    }
                }
                override fun onCancelled(dbErr: DatabaseError) {
                }
            })
        }
    }

}

class guestUser():readOnly(){
    val database = FirebaseDatabase.getInstance("https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
    init {
        username = "Guest"
    }
    override fun getPhotoLink(): String? {
        TODO("Get default image from firebase storage and set it to profilePhotoCache")
    }

    override fun daftarBaru(dataUser:dataUser){
        //Check for existing username
        database.child(dataUser.username).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(dataUser::class.java)
                //On username not found, allow user to use input username
                if (user == null) {
                    database.child(dataUser.username).setValue(dataUser)
                }
            }
            override fun onCancelled(dbErr: DatabaseError) {
            }
        })
    }
}

data class dataUser(val username: String,
                    var password: String?,
                    var email: String?,
                    var nama: String?,
                    var saldo: Int,
                    var url: String?
                    )