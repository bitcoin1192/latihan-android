package com.sisalma.movieticketapp

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.sisalma.movieticketapp.appActivity.Film

class viewModelFilm(filmRepository: filmRepository, lifecycleOwner: LifecycleOwner): ViewModel() {
    val filmRepo = filmRepository.getFilmData()

    private val films: MutableLiveData<ArrayList<Film>> = MutableLiveData<ArrayList<Film>>()
    private var datalist: ArrayList<Film> = ArrayList<Film>()

    init {
        defaultValue()

        filmRepo.observe(lifecycleOwner, Observer {
            films.value = it
        })
    }

    fun getFilmData(): LiveData<ArrayList<Film>> {
        return films
    }

    private fun defaultValue(){
        //Add default value when data hasn't arrive
        for(i in 1..4){
            datalist.add(Film("","",
            "","",
            HashMap(),"",
            ""))
        }
        films.value = datalist
    }
}