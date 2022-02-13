package com.sisalma.movieticketapp.repository

import androidx.lifecycle.LiveData
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.dataUser
import com.sisalma.movieticketapp.dataStructure.TicketData

class UserRepository (userAuthenticated: authenticatedUsers){
    val userObj = userAuthenticated

    fun getUserTicket(): LiveData<ArrayList<TicketData>> {
        return userObj.ticketDataList
    }

    fun getUserProfile(): LiveData<dataUser>{
        return userObj.userProfile
    }

    fun setUserProfile(email:String?,nama:String?,password:String?,saldo:Int?,url:String?){
        userObj.updateUserData(nama,email,password,saldo,url)
    }
}