package com.sisalma.movieticketapp

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*

abstract class Users() {
    lateinit var username: String
    lateinit var nama: String
    abstract fun getPhotoLink(): String?
}

abstract class readOnly: Users() {
    abstract fun daftarBaru(dataUser: dataUser)
}
abstract class readWrite: Users(){
    abstract fun userAuthenticate(inputUser: String, inputPass: String)
    abstract fun userDeauthenticate(): String
    abstract fun getUserData():dataUser?
    abstract fun updateUserData(nama:String?, email: String?, password: String?)
    abstract fun setProfilePic()
    abstract fun getTicketHistory():ArrayList<Movie>
}
class authenticatedUsers():readWrite(){
    lateinit var user: dataUser
    var authenticated = false
    var authFailed  = true
    val database = FirebaseDatabase.getInstance("https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")

    fun isAuthFailed(): Boolean{
        if(authFailed){
            return true
        }
        return false
    }
    fun isAuthenticated(): Boolean{
        if(authenticated){
            return true
        }
        return false

    }
    override fun userDeauthenticate(): String{
        TODO("Local Deauth by ending authenticated user lifecycle")
    }

    override fun setProfilePic() {
        TODO("Not yet implemented")
    }

    override fun getUserData():dataUser? {
        //Check for authenticated flags
        if(!authenticated){
            return user
        }else{
            return null
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
                    database.child(user.username).setValue(user)
                }
                override fun onCancelled(p0: DatabaseError) {

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

    override fun userAuthenticate(inputUser: String, inputPass: String) {
        if (inputUser.isNullOrEmpty() or inputPass.isNullOrEmpty()) {
            authenticated = false
            authFailed = true
        } else {
            database.child(inputUser).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var data = dataSnapshot.getValue(dataUser::class.java)
                    Log.e("Auth","Test")
                    //On username found, check flags and assign user with data
                    if (data != null) {
                        if (inputPass == data.password.toString()) {
                            Log.e("Auth",data.password.toString())
                            authenticated = true
                            authFailed = false
                            user = data
                        } else {
                            authenticated = false
                            authFailed = true
                        }
                    }else{
                        authFailed = true
                    }

                }

                override fun onCancelled(p0: DatabaseError) {
                    authFailed = true
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

data class dataUser(@get:PropertyName("username") @set:PropertyName("username") var username: String = "",
                    @get:PropertyName("password") @set:PropertyName("password") var password: String? = "",
                    @get:PropertyName("email") @set:PropertyName("email") var email: String? = "",
                    @get:PropertyName("nama") @set:PropertyName("nama") var nama: String? = "",
                    @get:PropertyName("saldo") @set:PropertyName("saldo") var saldo: String = "",
                    @get:PropertyName("url") @set:PropertyName("url") var url: String? = ""
                    )