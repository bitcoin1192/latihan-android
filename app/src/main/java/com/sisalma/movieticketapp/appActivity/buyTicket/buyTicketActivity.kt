package com.sisalma.movieticketapp.appActivity.buyTicket

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.databinding.ActivityBuyTicketBinding

class buyTicketActivity(): AppCompatActivity() {
    lateinit var uiBind: ActivityBuyTicketBinding
    lateinit var fragmentArrayList: ArrayList<Fragment>
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
        uiBind = ActivityBuyTicketBinding.inflate(layoutInflater)
        setContentView(uiBind.root)

        val seatFragment = seatSelectionFragment(filmName)
        fragmentArrayList = arrayListOf<Fragment>(seatFragment,
            paymentConfirmFragment(authUser, filmName),
            paymentFinishFragment(authUser, filmName))
        uiBind.fragmentHolder.adapter = pageHolder(this,fragmentArrayList)
        uiBind.fragmentHolder.isUserInputEnabled = false
    }

    fun nextPage(){
        if(uiBind.fragmentHolder.currentItem <= uiBind.fragmentHolder.childCount){
            uiBind.fragmentHolder.setCurrentItem(uiBind.fragmentHolder.currentItem + 1)
        }
        val seatFragment = fragmentArrayList[0] as seatSelectionFragment
        Log.i("seatResultActivity", seatFragment.returnSeatResult().toString())
    }

    fun backPage(){
        if(uiBind.fragmentHolder.currentItem >= 0 ){
            uiBind.fragmentHolder.setCurrentItem(uiBind.fragmentHolder.currentItem-1)
        }else{
            finish()
        }
    }
}