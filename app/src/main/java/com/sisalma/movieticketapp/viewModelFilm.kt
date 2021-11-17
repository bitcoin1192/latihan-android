package com.sisalma.movieticketapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.sisalma.movieticketapp.appActivity.Film

class viewModelFilm: ViewModel() {
    var mDatabase = FirebaseDatabase.getInstance("https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Film")
    init {
        loadFilm()
    }
    private var datalist = ArrayList<Film>()
    private val films: MutableLiveData<ArrayList<Film>> by lazy{
        MutableLiveData<ArrayList<Film>>()
    }

    fun getFilmData(): LiveData<ArrayList<Film>> {
        return films
    }

    private fun defaultValue(){
        //TODO("Add default value when data hasn't arrive")
        for(i in 1..4){
            datalist
        }
    }
    private fun loadFilm(){
        mDatabase.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                datalist.clear()
                Log.e("test-child", data.childrenCount.toString())
                for (filmdata in data.getChildren()){
                    if(filmdata != null) {
                        Log.e("test-child", filmdata.getValue(Film::class.java).toString())
                        datalist.add(filmdata.getValue(Film::class.java)!!)
                    }
                }
                films.value = datalist
        }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun loadAktorfromMap(AktorHashmap: HashMap<String,HashMap<String,String>>){

    }
}