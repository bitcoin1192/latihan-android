package com.sisalma.movieticketapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sisalma.movieticketapp.dataStructure.seat

class ViewModelUser: ViewModel() {
    /*private var profilePictureURL: String = ""
    private var currentSaldo: Int = 0
    private var userNickname: String = ""*/
    var _userData = dataUser()
    val userData: MutableLiveData<dataUser> = MutableLiveData()

    fun setUserData(inputUserData: dataUser){
        _userData = inputUserData
        userData.value = _userData
    }

    fun getUserData(): LiveData<dataUser>{
        return userData
    }

    fun getUserHistoryTicket(){

    }

    fun getUserHistoryTopup(){

    }
}