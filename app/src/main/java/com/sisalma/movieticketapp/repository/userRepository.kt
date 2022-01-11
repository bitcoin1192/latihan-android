package com.sisalma.movieticketapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.dataUser
import com.sisalma.movieticketapp.dataStructure.ticketData

class userRepository (userAuthenticated: authenticatedUsers){
    val userObj = userAuthenticated

    fun getUserTicket(): LiveData<ArrayList<ticketData>> {
        return userObj.userOwnTicket
    }

    fun getUserProfile(): LiveData<dataUser>{
        return userObj.userProfile
    }

    fun getUserSeat(namaFilm: String): HashMap<String,Int>{
        Log.i("userSeat",userObj.userOwnSeat.toString())
        return userObj.userOwnSeat[namaFilm]!!
    }

    fun setUserProfile(email:String?,nama:String?,password:String?,saldo:Int?,url:String?){
        userObj.updateUserData(nama,email,password,saldo,url)
    }
}