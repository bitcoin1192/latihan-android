package com.sisalma.movieticketapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sisalma.movieticketapp.dataStructure.Seat
import com.sisalma.movieticketapp.dataStructure.SesiTayang

class ViewModelCinema(): ViewModel() {
    private var _namaFilm = ""
    private var _seatArray: ArrayList<SesiTayang> = ArrayList()
    private var _selectedseatArray: ArrayList<Seat> = ArrayList()
    private var _seatHashMap: HashMap<String, SesiTayang> = HashMap()
    private var cancelItem = Seat()
    val cinemaData: MutableLiveData<SesiTayang>  = MutableLiveData()
    val selectedSeat: MutableLiveData<ArrayList<Seat>> = MutableLiveData()

    fun setNamaFilm(input: String){
        _namaFilm = input
        refreshSeatMap()
    }

    fun getNamaFilm(): String{
        return _namaFilm
    }
    fun getAvailableSeat(): LiveData<SesiTayang> {
        return cinemaData
    }

    fun liveSelectedSeat(): LiveData<ArrayList<Seat>>{
        return selectedSeat
    }

    //Observe cinemaRepository from activity
    fun setSesiTayang(input: ArrayList<SesiTayang>) {
        _seatArray = input
        createKeySesiTayang()
        mergeSeat()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setSelectedSeat(input: Seat){
        _selectedseatArray.find{ input.seatRow == it.seatRow && input.seatID == it.seatID }.run {
            if(this != null) {
                cancelItem = this.copy()
                _selectedseatArray.removeIf{
                    it.seatID == this.seatID && it.seatRow == this.seatRow
                }
            }else{
                _selectedseatArray.add(input)
            }
        }
        selectedSeat.value = _selectedseatArray
        mergeSeat()
    }
    private fun mergeSeat(){
        var temp = _seatHashMap.get(_namaFilm)
        _selectedseatArray.forEach { selectedSeat ->
            temp?.availableSeat?.find {
                it.seatRow == selectedSeat.seatRow && it.seatID == selectedSeat.seatID && it.statusAvailable
            }?.run {
                this.statusSelected = true
            }
        }
        temp?.availableSeat?.find {
            it.seatID == cancelItem.seatID && it.seatRow == cancelItem.seatRow
        }?.run {
            this.statusSelected = false
            cancelItem = Seat()
        }
        if(temp != null) {
            _seatHashMap[_namaFilm] = temp
        }
        refreshSeatMap()
    }
    fun getSelectedSeat():ArrayList<Seat> {
        return _selectedseatArray
    }

    private fun createKeySesiTayang(){
        _seatHashMap.clear()
        _seatArray.forEach{
            _seatHashMap.put(it.namaFilm,it)
        }
    }

    private fun refreshSeatMap(){
        _seatHashMap[_namaFilm]?.let { sesiTayang ->
            cinemaData.value = sesiTayang
        }
    }
}