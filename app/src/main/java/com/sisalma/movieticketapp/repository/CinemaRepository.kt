package com.sisalma.movieticketapp.repository

import androidx.lifecycle.MutableLiveData
import com.sisalma.movieticketapp.FirebaseSourceCinemaData
import com.sisalma.movieticketapp.dataStructure.CinemaDetail
import com.sisalma.movieticketapp.dataStructure.SesiTayang

class CinemaRepository{
    private val data = FirebaseSourceCinemaData()
    private var seatAvailableLiveData: MutableLiveData<ArrayList<Boolean>> = MutableLiveData<ArrayList<Boolean>>()

    init {
        data.attachCinemaData()
        data.attachSessionData()
    }

    fun getCinemaDetail(): MutableLiveData<ArrayList<CinemaDetail>>{
        //cinemaDetailList = data.getCinemaList()
        //cinemaDetailLiveData.value = cinemaDetailList
        return data.liveDataCinemaDetail
    }

    fun getSessionData(): MutableLiveData<ArrayList<SesiTayang>>{
        //sessionData = data.getCinemaSessionList()
        //sessionDataLiveData.value = sessionData
        return data.liveDataSessionData
    }

    fun getSeatAvailability(): MutableLiveData<ArrayList<Boolean>>{
        return seatAvailableLiveData
    }

}