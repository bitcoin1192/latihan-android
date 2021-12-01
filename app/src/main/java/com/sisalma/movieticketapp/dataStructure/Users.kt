package com.sisalma.movieticketapp

import android.content.Intent
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.sisalma.movieticketapp.appActivity.home
import kotlinx.coroutines.delay
import kotlinx.parcelize.Parcelize
import java.util.*

abstract class Users() {
    lateinit var username: String
    lateinit var nama: String
}

abstract class readOnly: Users() {
    abstract fun daftarBaru(dataUser: dataUser)
}
abstract class readWrite: Users(){
    abstract fun userAuthenticate(inputUser: String, inputPass: String)
    abstract fun userDeauthenticate(): String
    abstract fun getUserData():dataUser?
    abstract fun updateUserData(nama:String?, email: String?, password: String?, saldo: Int?, url: String?)
    abstract fun setProfilePic()
}
class authenticatedUsers():readWrite(){
    var user = dataUser()
    var authenticated = false
    var authFailed  = false
    val database = FirebaseDatabase.getInstance("https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
    val storageRef  = FirebaseStorage.getInstance().getReference("Photos")

    fun isAuthFailed(): Boolean{
        return authFailed
    }

    fun isAuthenticated(): Boolean{
        return authenticated
    }

    fun uploadImagetoFBStore(filepath: Uri){
        var path = storageRef.child(UUID.randomUUID().toString())
        path.putFile(filepath)
            .addOnSuccessListener {
                path.downloadUrl.addOnSuccessListener {
                    //savetoFirebase(path.path)
                    Log.i("uploadProfPic",it.toString())
                    updateUserData(null,null,null,null, it.toString())
                }

            }
            .addOnFailureListener {
                    e ->
            }
    }

    override fun userDeauthenticate(): String{
        TODO("Local Deauth by ending authenticated user lifecycle")
    }

    override fun setProfilePic() {
        TODO("upload image then update url user data")

    }

    override fun getUserData():dataUser? {
        //Check for authenticated flags
        if(authenticated){
            return user
        }else{
            return null
        }
    }

    override fun updateUserData(nama:String?, email: String?, password: String?, saldo: Int?, url: String?) {
        if(authenticated){
            database.child(user.username).addListenerForSingleValueEvent(object : ValueEventListener{
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
                    if(saldo != null){
                        user.saldo += saldo
                    }
                    if(!url.isNullOrEmpty()){
                        user.url = url
                    }
                    Log.i("updateInfo",user.toString())
                    database.child(user.username).setValue(user)
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("updateUserData",p0.message)
                }
            })
        }
    }

    override fun userAuthenticate(inputUser: String, inputPass: String) {
        authenticated = false
        authFailed  = false
        Log.e("Auth",inputUser+"/"+inputPass)
        if (inputUser.isNullOrEmpty() or inputPass.isNullOrEmpty()) {
            authenticated = false
            authFailed = true
        } else {
            database.child(inputUser).addListenerForSingleValueEvent(object : ValueEventListener {
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

    suspend fun testAuthorizeUser(authenticatedUsers: authenticatedUsers): Boolean{
        var counter = 0
        while (counter <= 4){
            if(authenticatedUsers.isAuthenticated()){
                Log.i("testAuthorize", "User found in try #$counter")
                return true
            }else if (authenticatedUsers.isAuthFailed()){
                Log.i("testAuthorize", "User or password not matching")
                return false
            }
            delay(500)
            counter++
        }
        Log.i("testAuthorize", "Counter exceed limits")
        return false
    }
}

class guestUser():readOnly(){
    val database = FirebaseDatabase.getInstance("https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")

    init {
        username = "Guest"
    }

    override fun daftarBaru(dataUser:dataUser){
        //Check for existing username
        database.child(dataUser.username).addListenerForSingleValueEvent(object : ValueEventListener {
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

@Parcelize
data class dataUser(@get:PropertyName("username") @set:PropertyName("username") var username: String = "",
                    @get:PropertyName("password") @set:PropertyName("password") var password: String? = "",
                    @get:PropertyName("email") @set:PropertyName("email") var email: String? = "",
                    @get:PropertyName("nama") @set:PropertyName("nama") var nama: String? = "",
                    @get:PropertyName("saldo") @set:PropertyName("saldo") var saldo: Int = 0,
                    @get:PropertyName("url") @set:PropertyName("url") var url: String? = ""
                    ):Parcelable