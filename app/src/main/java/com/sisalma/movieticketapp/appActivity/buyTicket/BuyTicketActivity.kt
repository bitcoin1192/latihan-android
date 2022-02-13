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
import com.sisalma.movieticketapp.appActivity.Home
import com.sisalma.movieticketapp.appActivity.appHome.ticketFragment.ViewModelTicket
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.databinding.ActivityBuyTicketBinding
import com.sisalma.movieticketapp.repository.CinemaRepository
import com.sisalma.movieticketapp.repository.UserRepository

class BuyTicketActivity : AppCompatActivity() {
    lateinit var uiBind: ActivityBuyTicketBinding
    lateinit var fragmentArrayList: ArrayList<Fragment>
    lateinit var authUser: authenticatedUsers
    var filmDetail: Film? = Film()
    val ViewModelCinema: ViewModelCinema by viewModels()
    val ViewModelNavTab: ViewModelNavTab by viewModels()
    val ViewModelUser: ViewModelUser by viewModels()
    val ViewModelTicket: ViewModelTicket by viewModels()
    val cinemaRepo = CinemaRepository()
    var transactionFinish = false
    lateinit var userRepo: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authUser = authenticatedUsers(applicationContext)
        authUser.userAuthenticate()
        userRepo = UserRepository(authUser)
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
        uiBind.fragmentHolder.adapter = PageHolder(this, fragmentArrayList)
        uiBind.fragmentHolder.isUserInputEnabled = false
    }

    override fun onBackPressed() {
        ViewModelNavTab.setCurrentPage(ViewModelNavTab.getCurrentPage()-1)
    }

    private fun ObserveRepository() {
        cinemaRepo.getSessionData().observe(this) {
            ViewModelCinema.setSesiTayang(it)
        }
        userRepo.getUserProfile().observe(this) {
            ViewModelUser.setUserData(it)
        }
        userRepo.getUserTicket().observe(this) {
            ViewModelTicket.setDataTicket(it)
            filmDetail?.let {
                ViewModelTicket.setfilmList(arrayListOf(it))
            }
        }
    }

    private fun ObserveViewModel() {
        ViewModelNavTab.liveCurrentPage().observe(this) { page ->
            if(transactionFinish){
                finishAffinity()
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            }else{
                if(page == fragmentArrayList.size - 1){
                    transactionFinish = true
                } else if(page < 0) {
                    finish()
                }
                uiBind.fragmentHolder.currentItem = page
            }

        }
        ViewModelUser.latestSeatRequest.observe(this) {
            filmDetail?.judul?.let { filmName ->
                authUser.buyTicket(filmName, it)
            }
        }
    }
}