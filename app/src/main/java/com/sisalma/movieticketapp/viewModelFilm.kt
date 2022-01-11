package com.sisalma.movieticketapp

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.repository.filmRepository

class viewModelFilm(): ViewModel() {
    private val films: MutableLiveData<ArrayList<Film>> = MutableLiveData<ArrayList<Film>>()
    private var datalist: ArrayList<Film> = ArrayList()

    init {
        defaultValue()
    }

    fun setFilmData(listFilmData: ArrayList<Film>){
        Log.i("filmViewModel-set",listFilmData.toString())
        datalist = listFilmData
        films.postValue(datalist)
    }

    fun getFilmData(): LiveData<ArrayList<Film>> {
        Log.i("filmViewModel-get",films.value.toString())
        return films
    }

    private fun defaultValue(){
        //Add default value when data hasn't arrive
        if (datalist.isEmpty()) {
            for (i in 1..4) {
                datalist.add(
                    Film(
                        "", "",
                        "", "",
                        HashMap(), "",
                        ""
                    )
                )
            }
            films.value = datalist
        }
    }
}