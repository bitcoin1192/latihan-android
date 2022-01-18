package com.sisalma.movieticketapp.appActivity.appHome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ViewModelNavTab: ViewModel() {
    private var _currentPage = 1
    val currentPage: MutableLiveData<Int> = MutableLiveData()

    fun liveCurrentPage():LiveData<Int>{
        return currentPage
    }

    fun getCurrentPage(): Int{
        return _currentPage
    }

    fun setCurrentPage(input: Int){
        _currentPage = input
        currentPage.value = _currentPage
    }

    fun triggerObserver(){
        currentPage.value = _currentPage
    }
}