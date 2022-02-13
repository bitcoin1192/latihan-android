package com.sisalma.movieticketapp.appActivity.appHome.ticketFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.dataStructure.TicketData

class ViewModelTicket : ViewModel() {
    var filmList: ArrayList<Film> = ArrayList()
    var ticketArrayList: ArrayList<TicketData> = ArrayList()
    val availableTicketFilms: MutableLiveData<ArrayList<TicketData>> =
        MutableLiveData<ArrayList<TicketData>>()
    private var _keyFilmData: HashMap<String, Film> = HashMap()
    var _keyTicketData: HashMap<String, TicketData> = HashMap()

    fun setfilmList(input: ArrayList<Film>) {
        filmList = input
        createKeyFilmData()
    }

    fun setDataTicket(input: ArrayList<TicketData>) {
        Log.i("vmt", "$input")
        ticketArrayList = input
        //ticketArrayList = arrayListOf(ticketData())
        calculateTotalPriceList()
        createKeyTicketData()
        availableTicketFilms.value = ticketArrayList
    }

    fun getTicketData(UID: String): TicketData? {
        return _keyTicketData[UID]
    }

    fun getFilmMapData(): HashMap<String, Film> {
        return _keyFilmData
    }

    fun getUserActiveTicket(): LiveData<ArrayList<TicketData>> {
        return availableTicketFilms
    }

    /*fun selectFilmByOwnedTicket(){
        if(filmList.isNotEmpty()){
            selectedFilmList.clear()
            for (ticket in ticketArrayList){
                if (searchKey(ticket.namaFilm)){
                    Log.i("selectFilm","Film object is found: ${_keyFilmData[ticket.namaFilm]}")
                    selectedFilmList.add(_keyFilmData[ticket.namaFilm] ?: Film())
                }
            }
            availableTicketFilms.value = selectedFilmList
        }
    }*/
    fun getFilmByKey(filmName: String): Film? {
        return _keyFilmData[filmName]
    }

    private fun createKeyFilmData() {
        _keyFilmData.clear()
        filmList.forEach({
            _keyFilmData.put(it.judul, it)
        })
    }

    private fun createKeyTicketData() {
        _keyTicketData.clear()
        ticketArrayList.forEach({
            _keyTicketData.put(it.UID, it)
        })
    }

    private fun calculateTotalPriceList() {
        ticketArrayList.forEach {
            var total = 0
            it.selectedSeat.forEach {
                total += it.priceList
            }
            it.seatPrice = total
        }

    }
}