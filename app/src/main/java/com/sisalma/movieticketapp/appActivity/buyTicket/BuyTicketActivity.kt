package com.sisalma.movieticketapp.appActivity.buyTicket

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sisalma.movieticketapp.ViewModelCinema
import com.sisalma.movieticketapp.ViewModelUser
import com.sisalma.movieticketapp.appActivity.appHome.ViewModelNavTab
import com.sisalma.movieticketapp.appActivity.buyTicket.paymentConfirmFragment.paymentConfirmFragment
import com.sisalma.movieticketapp.appActivity.buyTicket.paymentFinishFragment.paymentFinishFragment
import com.sisalma.movieticketapp.appActivity.buyTicket.seatSelectionFragment.SeatSelectionFragment
import com.sisalma.movieticketapp.appActivity.home
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.databinding.ActivityBuyTicketBinding
import com.sisalma.movieticketapp.repository.cinemaRepository
import com.sisalma.movieticketapp.repository.userRepository

class BuyTicketActivity : AppCompatActivity() {
    lateinit var uiBind: ActivityBuyTicketBinding
    lateinit var fragmentArrayList: ArrayList<Fragment>
    lateinit var authUser: authenticatedUsers
    var filmDetail: Film? = Film()
    val ViewModelCinema: ViewModelCinema by viewModels()
    val ViewModelNavTab: ViewModelNavTab by viewModels()
    val ViewModelUser: ViewModelUser by viewModels()
    val cinemaRepo = cinemaRepository()
    lateinit var userRepo: userRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authUser = authenticatedUsers(applicationContext)
        authUser.userAuthenticate()
        userRepo = userRepository(authUser)
        filmDetail = intent.getParcelableExtra("filmData")
        filmDetail?.judul?.let {
            ViewModelCinema.setNamaFilm(it)
        }
        uiBind = ActivityBuyTicketBinding.inflate(layoutInflater)
        setContentView(uiBind.root)

        filmDetail?.let {
            fragmentArrayList = arrayListOf(
                SeatSelectionFragment(),
                paymentConfirmFragment(),
                paymentFinishFragment()
            )
        }

        ObserveRepository()
        ObserveViewModel()
        uiBind.fragmentHolder.adapter = pageHolder(this, fragmentArrayList)
        uiBind.fragmentHolder.isUserInputEnabled = false
    }

    override fun onBackPressed() {
        backPage()
    }

    private fun nextPage() {
        if(uiBind.fragmentHolder.currentItem <= uiBind.fragmentHolder.childCount) {
            uiBind.fragmentHolder.currentItem = uiBind.fragmentHolder.currentItem + 1
        } else {
            finishAffinity()
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }
        val seatFragment = fragmentArrayList[0] as SeatSelectionFragment
        //Log.i("seatResultActivity", seatFragment.returnSeatResult().toString())
    }

    private fun backPage() {
        if(uiBind.fragmentHolder.currentItem > 0) {
            uiBind.fragmentHolder.currentItem = uiBind.fragmentHolder.currentItem - 1
        } else {
            finish()
        }
    }

    private fun ObserveRepository() {
        cinemaRepo.getSessionData().observe(this, {
            ViewModelCinema.setSesiTayang(it)
        })
        userRepo.getUserProfile().observe(this, {
            ViewModelUser.setUserData(it)
        })
    }

    private fun ObserveViewModel() {
        ViewModelNavTab.liveCurrentPage().observe(this, { page ->
            if(!(page < 0 || page > fragmentArrayList.size - 1)) {
                uiBind.fragmentHolder.currentItem = page
            } else {
                finish()
            }
        })
        ViewModelUser.latestSeatRequest.observe(this, {
            filmDetail?.judul?.let { filmName ->
                authUser.buyTicket(filmName, it)
            }
        })
    }
}