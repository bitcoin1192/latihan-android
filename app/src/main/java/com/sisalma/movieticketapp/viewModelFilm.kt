package com.sisalma.movieticketapp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.repository.filmRepository

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
            datalist.add(
                Film("","",
            "","",
            HashMap(),"",
            "")
            )
        }
        films.value = datalist
    }
}