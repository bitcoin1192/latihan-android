package com.sisalma.movieticketapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sisalma.movieticketapp.dataStructure.Film

open class FilmRepository {
    val instanceOfFirebase = "https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/"
    var listFilm = FirebaseDatabase
        .getInstance(instanceOfFirebase)
        .getReference("Film")

    private var _filmList: ArrayList<Film> = ArrayList()
    val filmList: MutableLiveData<ArrayList<Film>> = MutableLiveData<ArrayList<Film>>()
    private var _keyFilmData: HashMap<String, Film> = HashMap()
    val keyFilmData: MutableLiveData<HashMap<String, Film>> = MutableLiveData()

    init {
        attachFilmData()
    }
    fun getFilmLiveData():MutableLiveData<ArrayList<Film>>{
        return filmList
    }

    fun getMapFilmData(): HashMap<String, Film>{
        return _keyFilmData
    }

    fun searchKey(filmName:String):Boolean{
        if(_keyFilmData.containsKey(filmName)){
            return true
        }
        return false
    }

    fun attachFilmData(){
        listFilm.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                _filmList.clear()
                _keyFilmData.clear()
                Log.e("filmDataListener", data.childrenCount.toString())
                for (item in data.getChildren()){
                    _filmList.add(item.getValue(Film::class.java)!!)
                    _keyFilmData.put(item.getValue(Film::class.java)!!.judul,item.getValue(Film::class.java)!!)
                }
                filmList.value = _filmList
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("filmDataListener",p0.message)
            }
        })
    }
}

class NewFilmRepository: FilmRepository(){
    fun GetFilmData(){

    }
}