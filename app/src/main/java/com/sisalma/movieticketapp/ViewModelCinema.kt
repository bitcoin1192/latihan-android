package com.sisalma.movieticketapp

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sisalma.movieticketapp.dataStructure.cinemaDetail
import com.sisalma.movieticketapp.dataStructure.seat
import com.sisalma.movieticketapp.dataStructure.sesiTayang
import com.sisalma.movieticketapp.repository.cinemaRepository

class ViewModelCinema(): ViewModel() {
    private var _namaFilm = ""
    private var _seatArray: ArrayList<sesiTayang> = ArrayList()
    private var _selectedseatArray: ArrayList<seat> = ArrayList()
    private var _seatHashMap: HashMap<String, sesiTayang> = HashMap()
    private var cancelItem = seat()
    val CinemaData: MutableLiveData<sesiTayang>  = MutableLiveData()

    fun setNamaFilm(input: String){
        _namaFilm = input
        refreshSeatMap()
    }

    fun getNamaFilm(): String{
        return _namaFilm
    }
    fun getAvailableSeat(): LiveData<sesiTayang> {
        return CinemaData
    }

    //Observe cinemaRepository from activity
    fun setSesiTayang(input: ArrayList<sesiTayang>) {
        _seatArray = input
        createKeySesiTayang()
        mergeSeat()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setSelectedSeat(input: seat){
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
            cancelItem = seat()
        }
        if(temp != null) {
            _seatHashMap[_namaFilm] = temp
        }
        refreshSeatMap()
    }
    fun getSelectedSeat():ArrayList<seat> {
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
            CinemaData.value = sesiTayang
        }
    }
}