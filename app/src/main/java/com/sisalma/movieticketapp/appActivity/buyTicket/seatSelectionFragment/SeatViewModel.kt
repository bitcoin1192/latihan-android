package com.sisalma.movieticketapp.appActivity.buyTicket.seatSelectionFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sisalma.movieticketapp.dataStructure.sesiTayang

class seatViewModel (): ViewModel() {
    val seatAvailable: MutableLiveData<ArrayList<ArrayList<Boolean>>> = MutableLiveData()
    private val seatArrayStatus: ArrayList<ArrayList<Boolean>> = ArrayList()
    val seatSelected: MutableLiveData<HashMap<String, Boolean>> = MutableLiveData()
    val seatGroup: ArrayList<Boolean> = ArrayList()

    init {
    /*    seatStatus.forEachIndexed { index, b ->
            seatGroup.add(b)
            if (((index + 1) % 4) == 0) {
                seatArrayStatus.add(seatGroup.clone() as java.util.ArrayList<Boolean>)
                if (index == seatStatus.size - 1) {
                    seatAvailable.value = seatArrayStatus
                }
                seatGroup.clear()
            }
        }*/
    }

    fun setSeatStatus(seatData: sesiTayang){

    }

    fun getSeatStatus(): MutableLiveData<ArrayList<ArrayList<Boolean>>>{
        return seatAvailable
    }

    fun setSelectedSeat(newSelectedSeat: HashMap<String, Boolean>){
        seatSelected.value = newSelectedSeat
    }
}