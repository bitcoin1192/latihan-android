package com.sisalma.movieticketapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sisalma.movieticketapp.dataStructure.CinemaDetail
import com.sisalma.movieticketapp.dataStructure.SesiTayang

class FirebaseSourceCinemaData {
    val instanceOfFirebase = "https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/"

    var listCinema = FirebaseDatabase
        .getInstance(instanceOfFirebase)
        .getReference("cinema")

    var listSession = FirebaseDatabase
        .getInstance(instanceOfFirebase)
        .getReference("sesiTayang")

    private var cinemaDetailList: ArrayList<CinemaDetail> = ArrayList()
    private var sessionDataList: ArrayList<SesiTayang> = ArrayList()

    val liveDataCinemaDetail: MutableLiveData<ArrayList<CinemaDetail>> = MutableLiveData()
    val liveDataSessionData: MutableLiveData<ArrayList<SesiTayang>> = MutableLiveData()

    init {
        attachCinemaData()
        attachSessionData()
    }

    fun getCinemaList():ArrayList<CinemaDetail>{
        return cinemaDetailList
    }

    fun getCinemaSessionList(): ArrayList<SesiTayang>{
        return sessionDataList
    }

    fun attachCinemaData(){
        listCinema.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                Log.i("CinemaDataListener", data.childrenCount.toString())
                cinemaDetailList.clear()
                for (item in data.children){
                    item.getValue(CinemaDetail::class.java)?.let {
                        cinemaDetailList.add(it)
                    }
                }
                liveDataCinemaDetail.value = cinemaDetailList
            }

            override fun onCancelled(errorMessages: DatabaseError) {
                Log.e("CinemaDataListener", errorMessages.message)
            }
        })
    }

    fun attachSessionData(){
        listSession.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                Log.i("sessionDataListener", data.childrenCount.toString())
                sessionDataList.clear()
                for (item in data.children){
                    item.getValue(SesiTayang::class.java)?.let {
                        sessionDataList.add(it)
                    }
                }
                liveDataSessionData.value = sessionDataList
                Log.i("sessionDataListener", sessionDataList.toString())
            }

            override fun onCancelled(errorMessages: DatabaseError) {
                Log.e("cinemaDataListener", errorMessages.message)
            }
        })
    }
}
