package com.sisalma.movieticketapp.appActivity.buyTicket.seatSelectionFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sisalma.movieticketapp.dataStructure.sesiTayang

class seatViewModel (): ViewModel() {
    val seatAvailable: MutableLiveData<ArrayList<ArrayList<Boolean>>> = MutableLiveData()
    private val seatArrayStatus: ArrayList<ArrayList<Boolean>> = ArrayList()
    val seatSelected: MutableLiveData<HashMap<String, Boolean>> = MutableLiveData()
    val seatGroup: ArrayList<Boolean> = ArrayList()

    fun setSeatStatus(seatData: sesiTayang){

    }

    fun getSeatStatus(): MutableLiveData<ArrayList<ArrayList<Boolean>>>{
        return seatAvailable
    }

    fun setSelectedSeat(newSelectedSeat: HashMap<String, Boolean>){
        seatSelected.value = newSelectedSeat
    }
}