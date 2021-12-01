package com.sisalma.movieticketapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.dataUser
import com.sisalma.movieticketapp.dataStructure.ticketData

class userRepository (userAuthenticated: authenticatedUsers){
    val userObj = userAuthenticated
    val instanceOfFirebase = "https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/"
    var userTicketActiveFB = FirebaseDatabase
        .getInstance(instanceOfFirebase)
        .getReference("ticketHistory")
        .child(userObj.getUserData()!!.username)
        .child("listActive")

    var userProfileFB = FirebaseDatabase
        .getInstance(instanceOfFirebase)
        .getReference("User")


    private var ticketDataList:ArrayList<ticketData> = ArrayList()
    private val userOwnTicket: MutableLiveData<ArrayList<ticketData>> = MutableLiveData()

    private var userProfile: MutableLiveData<dataUser> = MutableLiveData()

    init {
        attachListener()
    }
    fun getUserTicket():MutableLiveData<ArrayList<ticketData>>{
        return userOwnTicket
    }

    fun getUserProfile(): MutableLiveData<dataUser>{
        return userProfile
    }

    fun attachListener(){
        attachProfileData()
        attachTicketData()
    }

    fun attachTicketData(){
        userTicketActiveFB.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                ticketDataList.clear()
                Log.i("attachTicketData", data.childrenCount.toString())
                for (data in data.getChildren()){
                    if(data != null) {
                        ticketDataList.add(data.getValue(ticketData::class.java)!!)
                    }
                }
                userOwnTicket.value = ticketDataList
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("ticketDataListener",p0.message)
            }
        })
    }

    fun attachProfileData(){
        userProfileFB.child(userObj.getUserData()!!.username).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                val user = data.getValue(dataUser::class.java)
                Log.i("attachProfileData", user.toString())
                userProfile.value = user
            }


            override fun onCancelled(p0: DatabaseError) {
                Log.e("profileDataListener",p0.toString())
            }
        })
    }
}