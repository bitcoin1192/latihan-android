package com.sisalma.movieticketapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sisalma.movieticketapp.dataStructure.Seat

class ViewModelUser: ViewModel() {
    /*private var profilePictureURL: String = ""
    private var currentSaldo: Int = 0
    private var userNickname: String = ""*/
    private var _latestSeatRequest: ArrayList<Seat> = ArrayList()
    val latestSeatRequest: MutableLiveData<ArrayList<Seat>> = MutableLiveData()
    private var _userData = dataUser()
    val userData: MutableLiveData<dataUser> = MutableLiveData()
    val logout: MutableLiveData<Boolean> = MutableLiveData()

    fun setUserData(inputUserData: dataUser){
        _userData = inputUserData
        userData.value = _userData
    }

    fun liveUserData(): LiveData<dataUser>{
        return userData
    }

    fun getUserData(): dataUser{
        return _userData
    }

    fun buyTicket(input: ArrayList<Seat>){
        _latestSeatRequest = input
        latestSeatRequest.value = _latestSeatRequest
    }

    fun getUserHistoryTicket(){

    }

    fun getUserHistoryTopup(){

    }

    fun userLogout(inputBool: Boolean){
        logout.value = inputBool
    }
}