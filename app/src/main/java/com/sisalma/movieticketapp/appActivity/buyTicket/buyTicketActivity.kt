package com.sisalma.movieticketapp.appActivity.buyTicket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.databinding.FragmentBuyTicketBinding

class buyTicketActivity(): AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //username and password is always available after login page
        val authUser = authenticatedUsers()
        val filmName = intent.getParcelableExtra<Film>("filmData")?.judul.toString()
        val username = applicationContext.getSharedPreferences("app-setting", MODE_PRIVATE)
            .getString("username","")!!
        val password = applicationContext.getSharedPreferences("app-setting", MODE_PRIVATE)
            .getString("password","")!!
        authUser.userAuthenticate(username,password)
        var uiBind = FragmentBuyTicketBinding.inflate(layoutInflater)
        setContentView(uiBind.root)

        val fragmentArrayList = arrayListOf<Fragment>(seatSelectionFragment(authUser,filmName),
            paymentConfirmFragment(authUser, filmName),
            paymentFinishFragment(authUser, filmName))
        uiBind.fragmentHolder.adapter = pageHolder(this,fragmentArrayList)
    }
}