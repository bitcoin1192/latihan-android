package com.sisalma.movieticketapp.appActivity.appHome.ticketFragment

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.dataStructure.seat
import com.sisalma.movieticketapp.repository.filmRepository
import com.sisalma.movieticketapp.dataStructure.ticketData
import com.sisalma.movieticketapp.repository.userRepository

class ViewModelTicket(): ViewModel() {
    var filmList: ArrayList<Film> = ArrayList()
    var ticketArrayList: ArrayList<ticketData> = ArrayList()
    private val availableTicketFilms: MutableLiveData<ArrayList<Film>> = MutableLiveData<ArrayList<Film>>()
    private var _keyFilmData: HashMap<String, Film> = HashMap()
    private var selectedFilmList: ArrayList<Film> = ArrayList()
    private var _keyTicketData: HashMap<String, ticketData> = HashMap()

    fun setfilmList(input: ArrayList<Film>){
        filmList = input
        createKeyFilmData()
        selectFilmByOwnedTicket()
    }

    fun setDataTicket(input: ArrayList<ticketData>){
        ticketArrayList = input
        createKeyTicketData()
        selectFilmByOwnedTicket()
    }

    fun getTicketData(filmName: String): ticketData? {
        return _keyTicketData[filmName]
    }

    fun getUserActiveTicket(): LiveData<ArrayList<Film>> {
        return availableTicketFilms
    }

    fun selectFilmByOwnedTicket(){
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
    }
    private fun searchKey(filmName: String):Boolean{
        if(_keyFilmData.containsKey(filmName)){
            return true
        }
        return false
    }

    private fun createKeyFilmData(){
        _keyFilmData.clear()
        filmList.forEach({
            _keyFilmData.put(it.judul,it)
        })
    }

    private fun createKeyTicketData(){
        _keyTicketData.clear()
        ticketArrayList.forEach({
            _keyTicketData.put(it.namaFilm,it)
        })
    }
}