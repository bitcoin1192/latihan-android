package com.sisalma.movieticketapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.internal.ConnectionErrorMessages
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.dataStructure.cinemaDetail
import com.sisalma.movieticketapp.dataStructure.sesiTayang

class cinemaRepository {
    val instanceOfFirebase = "https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/"
    var listCinema = FirebaseDatabase
        .getInstance(instanceOfFirebase)
        .getReference("cinema")

    var listSession = FirebaseDatabase
        .getInstance(instanceOfFirebase)
        .getReference("sesiTayang")

    private var cinemaDetailList: ArrayList<cinemaDetail> = ArrayList()
    private val cinemaDetailLiveData: MutableLiveData<ArrayList<cinemaDetail>> = MutableLiveData<ArrayList<cinemaDetail>>()
    private val sessionData: ArrayList<sesiTayang> = ArrayList()
    private val sessionDataLiveData: MutableLiveData<ArrayList<sesiTayang>> = MutableLiveData<ArrayList<sesiTayang>>()
    private var seatAvailable: ArrayList<Boolean> = ArrayList()
    private var seatAvailableLiveData: MutableLiveData<ArrayList<Boolean>> = MutableLiveData<ArrayList<Boolean>>()

    init {
        attachCinemaData()
        attachSessionData()
    }

    fun getCinemaDetail(): MutableLiveData<ArrayList<cinemaDetail>>{
        return cinemaDetailLiveData
    }

    fun getSessionData(): MutableLiveData<ArrayList<sesiTayang>>{
        return sessionDataLiveData
    }

    fun getSeatAvailability(): MutableLiveData<ArrayList<Boolean>>{
        return seatAvailableLiveData
    }

    fun attachCinemaData(){
        listCinema.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(data: DataSnapshot) {
                Log.i("cinemaDataListener", data.childrenCount.toString())
                for (item in data.children){
                    cinemaDetailList.add(item.getValue(cinemaDetail::class.java)!!)
                }
                cinemaDetailLiveData.value = cinemaDetailList
            }

            override fun onCancelled(errorMessages: DatabaseError) {
                Log.e("cinemaDataListener", errorMessages.message)
            }
        })
    }
    fun attachSessionData(){
        listSession.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(data: DataSnapshot) {
                Log.i("sessionDataListener", data.childrenCount.toString())
                for (item in data.children){
                    sessionData.add(item.getValue(sesiTayang::class.java)!!)
                    for(seat in item.child("availableSeat").children){
                        seatAvailable.add(seat.value as Boolean)
                    }
                }
                sessionDataLiveData.value = sessionData
                Log.i("seatArray",seatAvailable.toString())
            }

            override fun onCancelled(errorMessages: DatabaseError) {
                Log.e("cinemaDataListener", errorMessages.message)
            }
        })
    }
}