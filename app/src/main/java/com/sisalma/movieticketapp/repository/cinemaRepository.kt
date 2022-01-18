package com.sisalma.movieticketapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.internal.ConnectionErrorMessages
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sisalma.movieticketapp.FirebaseSourceCinemaData
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.dataStructure.cinemaDetail
import com.sisalma.movieticketapp.dataStructure.sesiTayang
import javax.inject.Inject

class cinemaRepository{
    private val data = FirebaseSourceCinemaData()
    private var seatAvailableLiveData: MutableLiveData<ArrayList<Boolean>> = MutableLiveData<ArrayList<Boolean>>()

    init {
        data.attachCinemaData()
        data.attachSessionData()
    }

    fun getCinemaDetail(): MutableLiveData<ArrayList<cinemaDetail>>{
        //cinemaDetailList = data.getCinemaList()
        //cinemaDetailLiveData.value = cinemaDetailList
        return data.LiveDataCinemaDetail
    }

    fun getSessionData(): MutableLiveData<ArrayList<sesiTayang>>{
        //sessionData = data.getCinemaSessionList()
        //sessionDataLiveData.value = sessionData
        return data.LiveDataSessionData
    }

    fun getSeatAvailability(): MutableLiveData<ArrayList<Boolean>>{
        return seatAvailableLiveData
    }

}