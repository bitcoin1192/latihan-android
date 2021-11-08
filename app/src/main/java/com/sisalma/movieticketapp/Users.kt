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
    //abstract fun getTicketHistory():ArrayList<T>
}
class authenticatedUsers():readWrite(){
    var user = dataUser()
    var authenticated = false
    var authFailed  = false
    val database = FirebaseDatabase.getInstance("https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")

    fun isAuthFailed(): Boolean{
        return authFailed
    }
    fun isAuthenticated(): Boolean{
        return authenticated
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
        if(authenticated){
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

    override fun getPhotoLink(): String? {
        TODO("Not yet implemented")
    }

    override fun userAuthenticate(inputUser: String, inputPass: String) {
        authenticated = false
        authFailed  = false
        Log.e("Auth",inputUser+"/"+inputPass)
        if (inputUser.isNullOrEmpty() or inputPass.isNullOrEmpty()) {
            authenticated = false
            authFailed = true
        } else {
            database.child(inputUser).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.i("Firebase-fetch","Data incoming")
                    var data = dataSnapshot.getValue(dataUser::class.java)
                    //On username found and password match, check flags and assign user with receive data
                    if (data != null) {
                        if (inputPass == data.password.toString()) {
                            authenticated = true
                            user = data
                        } else {
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