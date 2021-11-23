package com.sisalma.movieticketapp.appActivity.appHome.ticketFragment

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.repository.filmRepository
import com.sisalma.movieticketapp.dataStructure.ticketData
import com.sisalma.movieticketapp.repository.userRepository

class viewTicketModel(userRepository: userRepository, filmRepository: filmRepository, lifecycleOwner: LifecycleOwner): ViewModel() {

    val filmObj = filmRepository

    val dataFilm = filmRepository.getFilmData()
    val dataTicket = userRepository.getUserTicket()
    var filmList: ArrayList<Film> = ArrayList()

    private val availableTicketFilms: MutableLiveData<ArrayList<Film>> = MutableLiveData<ArrayList<Film>>()

    private var selectedFilmList: ArrayList<Film> = ArrayList<Film>()

    init {
        dataFilm.observe(lifecycleOwner,{ newFilmList ->
            Log.i("dataFilm","Observer")
            filmList = newFilmList
        })

        dataTicket.observe(lifecycleOwner,{
            Log.i("dataTicket","Observer")
            selectFilmByOwnedTicket(it)
        })
    }

    fun getUserActiveTicket(): MutableLiveData<ArrayList<Film>>{
        return availableTicketFilms
    }

    fun selectFilmByOwnedTicket(ticketArrayList: ArrayList<ticketData>){
        if(filmList.isNotEmpty()){
            selectedFilmList.clear()
            for (ticket in ticketArrayList){
                if (filmObj.searchKey(ticket.namaFilm)){
                    Log.i("selectFilm","Film object is found: ${filmObj.getMapFilmData()[ticket.namaFilm]}")
                    selectedFilmList.add(filmObj.getMapFilmData()[ticket.namaFilm] ?: Film())
                }
            }
            availableTicketFilms.value = selectedFilmList
        }
    }

}