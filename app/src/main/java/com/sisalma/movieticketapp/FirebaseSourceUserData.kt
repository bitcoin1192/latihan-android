package com.sisalma.movieticketapp

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.sisalma.movieticketapp.dataStructure.seat
import com.sisalma.movieticketapp.dataStructure.ticketData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.parcelize.Parcelize
import java.util.*

abstract class Users(applicationContext: Context) {
    val applicationContext = applicationContext
    val settings = applicationContext.getSharedPreferences("app-setting",MODE_PRIVATE)
    val settingEditor = settings.edit()
    lateinit var username: String
    lateinit var password: String
    lateinit var user: dataUser
    var authenticated = false
    var authFailed  = false
    val userProfileData = FirebaseDatabase
        .getInstance()
        .getReference("User")
    val userTicketData = FirebaseDatabase
        .getInstance()
        .getReference("ticketHistory")
    val storageRef  = FirebaseStorage
        .getInstance()
        .getReference("Photos")
    var ticketDataList:ArrayList<ticketData> = ArrayList()
    val userOwnTicket: MutableLiveData<ArrayList<ticketData>> = MutableLiveData()
    val userOwnSeat: HashMap<String, HashMap<String,Int>> = HashMap()
    var userProfile: MutableLiveData<dataUser> = MutableLiveData()
}

interface GuestOnly{
    fun daftarBaru(dataUser: dataUser)
    fun userAuthenticate(inputUser: String, inputPass: String)
}

interface AuthenticatedUser{
    fun userAuthenticate()
    fun userDeauthenticate()
    fun getUserData():dataUser?
    //fun updateUserData(nama:String?, email: String?, password: String?, saldo: Int?, url: String?)
    fun updateUserFilmHistory(namaFilm: String, seatSelected: List<seat>)
    fun uploadImagetoFBStore(filepath: Uri)
}

class authenticatedUsers(applicationContext: Context): AuthenticatedUser, Users(applicationContext){
    init {
        username = settings.getString("username","")?:""
        password = settings.getString("password","")?:""
        attachProfileData()
        attachTicketData()
    }

    fun buyTicket(namaFilm: String, seatSelected: List<seat>){
        updateUserFilmHistory(namaFilm,seatSelected)
    }

    override fun uploadImagetoFBStore(filepath: Uri){
        var path = storageRef.child(UUID.randomUUID().toString())
        path.putFile(filepath)
            .addOnSuccessListener {
                path.downloadUrl.addOnSuccessListener {
                    //savetoFirebase(path.path)
                    Log.i("uploadProfPic",it.toString())
                    updateUserData(null,null,null,null, it.toString())
                }
            }
            .addOnFailureListener { e ->
                Log.i("uploadProfPic", e.message.toString())
            }
    }

    override fun userDeauthenticate(){
        //Remove unique username from sharedpreferences
        settingEditor.remove("username")
        settingEditor.remove("password")
        settingEditor.commit()
    }

    override fun getUserData():dataUser? {
        if(authenticated){
            return user
        }else{
            return null
        }
    }

    fun potongSaldo(request:Int){
        if(authenticated) {
            updateUserData(null, null, null,user.saldo - request, null)
        }
    }

    fun topupSaldo(request:Int){
        Log.i("damint", "$username")
        if(authenticated) {
            updateUserData(null, null, null, request + user.saldo, null)
        }
    }

    fun updateUserData(nama:String?, email: String?, password: String?, saldo: Int?, url: String?) {
        Log.i("updateInfo","$username and $authenticated")
        if(authenticated){
            userProfileData.child(username).addListenerForSingleValueEvent(object : ValueEventListener{
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
                        user.saldo = saldo
                    }
                    if(!url.isNullOrEmpty()){
                        user.url = url
                    }
                    Log.i("updateInfo","is ${user.toString()}")
                    userProfileData.child(user.username).setValue(user)
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("updateUserData",p0.message)
                }
            })
        }
    }

    override fun updateUserFilmHistory(namaFilm: String, seatSelected: List<seat>) {
        val ticketBuild = ticketData("22/01/2022","ticketQR",namaFilm)
        if(authenticated){
            userTicketData.child(username)
                .child("listActive")
                .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.i("updateUserTicketInfo",ticketBuild.toString())
                    userTicketData.child(username).child("listActive").child(UUID.randomUUID().toString()).setValue(ticketBuild)
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("updateUserData",p0.message)
                }
            })
        }
    }

    override fun userAuthenticate() {
        authenticated = false
        authFailed  = false
        Log.e("Auth",username+"/"+password)
        //As long username and password not empty, we can be sure
        //Authentication in login page is successful
        if (username.isNotEmpty() && password.isNotEmpty()) {
            authenticated = true
            authFailed = false
        } else {
            authenticated = false
            authFailed = true
        }
    }

    fun attachTicketData(){
        userTicketData
            .child(username).child("listActive")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(data: DataSnapshot) {
                    ticketDataList.clear()
                    userOwnSeat.clear()
                    Log.i("attachTicketData", data.childrenCount.toString())
                    for (data in data.getChildren()){
                        val test = HashMap<String,Int>()
                        if(data != null) {
                            ticketDataList.add(data.getValue(ticketData::class.java)!!)
                            Log.i("attachTicketData", ticketDataList.toString())
                        }
                        for(seat in data.child("selectedSeat").children){
                            Log.i("test",seat.key.toString()+seat.value.toString())
                            test.put(seat.key as String,0)
                        }
                        userOwnSeat.put(data.getValue(ticketData::class.java)!!.namaFilm, test)
                    }
                    userOwnTicket.value = ticketDataList
                }

                override fun onCancelled(p0: DatabaseError) {
                    Log.e("ticketDataListener",p0.message)
                }
            })
    }

    fun attachProfileData(){
        Log.i("attachProfileData", "$username is found")
        userProfileData.child(username).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                data.getValue(dataUser::class.java)?.let {
                    user = it
                }?:run {
                    user = dataUser()
                }
                Log.i("attachProfileData", user.toString())
                userProfile.value = user
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("profileDataListener",p0.toString())
            }
        })
    }
}

class GuestUser(applicationContext: Context):GuestOnly, Users(applicationContext){
    override fun daftarBaru(dataUser:dataUser){
        //Check for existing username
        userProfileData.child(dataUser.username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(dataUser::class.java)
                //On username not found, allow user to use input username
                if (user == null) {
                    userProfileData.child(dataUser.username).setValue(dataUser)
                    settingEditor.putString("username",dataUser.username)
                    settingEditor.putString("password",dataUser.password)
                    settingEditor.commit()
                }
            }
            override fun onCancelled(dbErr: DatabaseError) {
            }
        })
    }

    override fun userAuthenticate(inputUser: String,inputPass: String) {
        authenticated = false
        authFailed = false
        userProfileData.child(inputUser).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("firebaseData", "$dataSnapshot")
                var user = dataSnapshot.getValue(dataUser::class.java)
                Log.i("username ", "$inputUser is $user")
                user?.let {
                    if(it.username.isNotEmpty() && inputUser == it.username && inputPass == it.password){
                        settingEditor.putString("username", it.username)
                        settingEditor.putString("password", it.password)
                        settingEditor.commit()
                        authenticated = true
                    }else{
                        authFailed = true
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                authFailed = true
            }
        })
    }

    /**
     * Test whether guestUser object is ready for use, when object is ready
     * this will return true, otherwise false for any reason.
     */
    suspend fun testAuthorizeUser(): Boolean{
        var counter = 0
        while (counter <= 4){
            if(authenticated){
                Log.i("testAuthorize", "User found in try #$counter")
                return true
            }else if (authFailed){
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

@Parcelize
data class dataUser(@get:PropertyName("username") @set:PropertyName("username") var username: String = "",
                    @get:PropertyName("password") @set:PropertyName("password") var password: String? = "",
                    @get:PropertyName("email") @set:PropertyName("email") var email: String? = "",
                    @get:PropertyName("nama") @set:PropertyName("nama") var nama: String? = "",
                    @get:PropertyName("saldo") @set:PropertyName("saldo") var saldo: Int = 0,
                    @get:PropertyName("url") @set:PropertyName("url") var url: String? = ""
                    ):Parcelable