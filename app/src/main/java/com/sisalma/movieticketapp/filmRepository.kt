package com.sisalma.movieticketapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sisalma.movieticketapp.appActivity.Film

class filmRepository {
    val instanceOfFirebase = "https://latihan-mta-default-rtdb.asia-southeast1.firebasedatabase.app/"
    var listFilm = FirebaseDatabase
        .getInstance(instanceOfFirebase)
        .getReference("Film")

    private var filmList: ArrayList<Film> = ArrayList()
    private val filmData: MutableLiveData<ArrayList<Film>> = MutableLiveData<ArrayList<Film>>()
    private var keyFilmData: HashMap<String,Film> = HashMap()

    init {
        attachFilmData()
    }
    fun getFilmData():MutableLiveData<ArrayList<Film>>{
        return filmData
    }

    fun getMapFilmData(): HashMap<String,Film>{
        return keyFilmData
    }

    fun searchKey(filmName:String):Boolean{
        if(keyFilmData.containsKey(filmName)){
            return true
        }
        return false
    }

    fun attachFilmData(){
        listFilm.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                filmList.clear()
                keyFilmData.clear()
                Log.e("filmDataListener", data.childrenCount.toString())
                for (item in data.getChildren()){
                    filmList.add(item.getValue(Film::class.java)!!)
                    keyFilmData.put(item.getValue(Film::class.java)!!.judul,item.getValue(Film::class.java)!!)
                }
                filmData.value = filmList
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("filmDataListener",p0.message)
            }
        })
    }
}