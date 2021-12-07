package com.sisalma.movieticketapp.appActivity.buyTicket.seatSelectionFragment

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.sisalma.movieticketapp.repository.cinemaRepository

class seatViewModel(cinemaRepository: cinemaRepository, lifecycleOwner: LifecycleOwner): ViewModel() {
    val cinemaRepo = cinemaRepository
    val test = cinemaRepo.getSeatAvailability()
    val seatAvailable: MutableLiveData<ArrayList<ArrayList<Boolean>>> = MutableLiveData()
    private val seatArrayStatus: ArrayList<ArrayList<Boolean>> = ArrayList()
    val seatSelected: MutableLiveData<HashMap<String, Boolean>> = MutableLiveData()
    val seatGroup: ArrayList<Boolean> = ArrayList()

    init {
        test.observe(lifecycleOwner, Observer { seatStatus ->
            Log.i("test","iam alive")
            seatArrayStatus.clear()
            //Weird code? yes. Anggep aja data dari cinemaRepo
            //belum di jadiin satu baris, ini kode buat ngegrup seatStatus
            //menjadi barisnya, secara berurutan. (index+1)%4 ini menunjukkan
            //baris tersebut memiliki empat seat, sehingga kita akan membuat grup
            //array berisi empat item.
            seatStatus.forEachIndexed{ index, b ->
                seatGroup.add(b)
                if (((index+1)%4) == 0){
                    seatArrayStatus.add(seatGroup.clone() as java.util.ArrayList<Boolean>)
                    if(index==seatStatus.size-1) {
                        seatAvailable.value = seatArrayStatus
                    }
                    seatGroup.clear()
                }
            }
        })
    }

    fun getSeatStatus(): MutableLiveData<ArrayList<ArrayList<Boolean>>>{
        return seatAvailable
    }

    fun setSelectedSeat(newSelectedSeat: HashMap<String, Boolean>){
        seatSelected.value = newSelectedSeat
    }
}