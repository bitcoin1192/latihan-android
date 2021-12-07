package com.sisalma.movieticketapp.appActivity.buyTicket

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sisalma.movieticketapp.appActivity.buyTicket.paymentConfirmFragment.paymentConfirmFragment
import com.sisalma.movieticketapp.appActivity.buyTicket.paymentFinishFragment.paymentFinishFragment
import com.sisalma.movieticketapp.appActivity.buyTicket.seatSelectionFragment.seatSelectionFragment
import com.sisalma.movieticketapp.appActivity.home
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.databinding.ActivityBuyTicketBinding
import com.sisalma.movieticketapp.repository.cinemaRepository

class buyTicketActivity(): AppCompatActivity() {
    lateinit var uiBind: ActivityBuyTicketBinding
    lateinit var fragmentArrayList: ArrayList<Fragment>
    lateinit var filmDetail: Film

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authUser = authenticatedUsers()
        val cinemaRepo = cinemaRepository()

        //username and password is always available after login page
        filmDetail = intent.getParcelableExtra<Film>("filmData")!!
        val username = applicationContext.getSharedPreferences("app-setting", MODE_PRIVATE)
            .getString("username","")!!
        val password = applicationContext.getSharedPreferences("app-setting", MODE_PRIVATE)
            .getString("password","")!!
        authUser.userAuthenticate(username,password)
        uiBind = ActivityBuyTicketBinding.inflate(layoutInflater)
        setContentView(uiBind.root)

        val seatFragment = seatSelectionFragment(filmDetail, cinemaRepo)
        fragmentArrayList = arrayListOf<Fragment>(seatFragment,
            paymentConfirmFragment(authUser, filmDetail.judul, seatFragment.returnSeatResult()),
            paymentFinishFragment(authUser, filmDetail.judul)
        )
        uiBind.fragmentHolder.adapter = pageHolder(this,fragmentArrayList)
        uiBind.fragmentHolder.isUserInputEnabled = false
    }

    override fun onBackPressed() {
        backPage()
    }
    fun nextPage(){
        if(uiBind.fragmentHolder.currentItem <= uiBind.fragmentHolder.childCount){
            uiBind.fragmentHolder.setCurrentItem(uiBind.fragmentHolder.currentItem + 1)
        }else{
            finishAffinity()
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }
        val seatFragment = fragmentArrayList[0] as seatSelectionFragment
        Log.i("seatResultActivity", seatFragment.returnSeatResult().toString())
    }

    fun backPage(){
        if(uiBind.fragmentHolder.currentItem > 0 ){
            uiBind.fragmentHolder.setCurrentItem(uiBind.fragmentHolder.currentItem-1)
        }else{
            finish()
        }
    }
}