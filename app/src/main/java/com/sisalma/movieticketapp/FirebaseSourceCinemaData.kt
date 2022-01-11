package com.sisalma.movieticketapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sisalma.movieticketapp.dataStructure.cinemaDetail
import com.sisalma.movieticketapp.dataStructure.sesiTayang

class FirebaseSourceCinemaData {
    val instanceOfFirebase = "https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/"

    var listCinema = FirebaseDatabase
        .getInstance(instanceOfFirebase)
        .getReference("cinema")

    var listSession = FirebaseDatabase
        .getInstance(instanceOfFirebase)
        .getReference("sesiTayang")

    private var CinemaDetailList: ArrayList<cinemaDetail> = ArrayList()
    private var SessionDataList: ArrayList<sesiTayang> = ArrayList()

    val LiveDataCinemaDetail: MutableLiveData<ArrayList<cinemaDetail>> = MutableLiveData()
    val LiveDataSessionData: MutableLiveData<ArrayList<sesiTayang>> = MutableLiveData()

    init {
        attachCinemaData()
        attachSessionData()
    }

    fun getCinemaList():ArrayList<cinemaDetail>{
        return CinemaDetailList
    }

    fun getCinemaSessionList(): ArrayList<sesiTayang>{
        return SessionDataList
    }

    fun attachCinemaData(){
        listCinema.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                Log.i("CinemaDataListener", data.childrenCount.toString())
                CinemaDetailList.clear()
                for (item in data.children){
                    item.getValue(cinemaDetail::class.java)?.let {
                        CinemaDetailList.add(it)
                    }
                }
                LiveDataCinemaDetail.value = CinemaDetailList
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
                SessionDataList.clear()
                for (item in data.children){
                    item.getValue(sesiTayang::class.java)?.let {
                        SessionDataList.add(it)
                    }
                }
                LiveDataSessionData.value = SessionDataList
                Log.i("sessionDataListener", SessionDataList.toString())
            }

            override fun onCancelled(errorMessages: DatabaseError) {
                Log.e("cinemaDataListener", errorMessages.message)
            }
        })
    }
}
