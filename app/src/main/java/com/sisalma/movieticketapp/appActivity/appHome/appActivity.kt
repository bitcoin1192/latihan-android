package com.sisalma.movieticketapp.appActivity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.ViewModelUser
import com.sisalma.movieticketapp.appActivity.appHome.ViewModelNavTab
import com.sisalma.movieticketapp.appActivity.appHome.homeFragment.dashboardFragment
import com.sisalma.movieticketapp.appActivity.appHome.ticketFragment.ViewModelTicket
import com.sisalma.movieticketapp.appActivity.appHome.ticketFragment.TicketFragment
import com.sisalma.movieticketapp.appActivity.appHome.userProfileFragment.SettingFragment
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.databinding.ActivityHomeBinding
import com.sisalma.movieticketapp.repository.FilmRepository
import com.sisalma.movieticketapp.repository.UserRepository
import com.sisalma.movieticketapp.ViewModelFilm

class Home : AppCompatActivity() {
    private var currFragment: Fragment? = null
    private val filmRepo = FilmRepository()
    private lateinit var userRepo: UserRepository
    private lateinit var binding: ActivityHomeBinding
    val ViewModelFilm: ViewModelFilm by viewModels()
    val viewModelUser: ViewModelUser by viewModels()
    val ViewModelTicket: ViewModelTicket by viewModels()
    val ViewModelNavTab: ViewModelNavTab by viewModels()
    private lateinit var fragmentHome: dashboardFragment
    private lateinit var fragmentTiket: TicketFragment
    private lateinit var fragmentSetting: SettingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settings = applicationContext.getSharedPreferences("app-setting", MODE_PRIVATE)
        val oldUser = settings.getBoolean("oldUser", false)

        val userObject = authenticatedUsers(applicationContext)
        userObject.userAuthenticate()
        userRepo = UserRepository(userObject)

        fragmentHome = dashboardFragment()
        fragmentTiket = TicketFragment()
        fragmentSetting = SettingFragment()

        observeRepository()
        observeViewModel()

        binding.ivMenu1.setOnClickListener {
            ViewModelNavTab.setCurrentPage(1)
        }

        binding.ivMenu2.setOnClickListener {
            ViewModelNavTab.setCurrentPage(2)
        }

        binding.ivMenu3.setOnClickListener {
            ViewModelNavTab.setCurrentPage(3)
        }

        ViewModelNavTab.triggerObserver()

        if(!oldUser) {
            settings.edit().putBoolean("oldUser", true).apply()
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if(currFragment != null) {
            if(fragment != currFragment) {
                fragmentTransaction.replace(R.id.layout_frame, fragment)
                fragmentTransaction.commit()
                currFragment = fragment
            } else {
                Log.i("setFragment", "Not replacing fragment because user selected same menu")
            }
        } else {
            fragmentTransaction.replace(R.id.layout_frame, fragment)
            fragmentTransaction.commit()
            currFragment = fragment
        }
    }

    private fun changeIcon(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }

    private fun observeRepository() {
        filmRepo.getFilmLiveData().observe(this, {
            ViewModelFilm.setFilmData(it)
            ViewModelTicket.setfilmList(it)
        })
        userRepo.getUserProfile().observe(this, {
            viewModelUser.setUserData(it)
        })
        userRepo.getUserTicket().observe(this, {
            ViewModelTicket.setDataTicket(it)
        })
    }

    private fun observeViewModel() {
        viewModelUser.logout.observe(this, {
            if(it) {
                userRepo.userObj.userDeauthenticate()
            }
        })
        ViewModelNavTab.liveCurrentPage().observe(this, {
            when (it) {
                1 -> {
                    changeIcon(binding.ivMenu1, R.drawable.ic_home_active)
                    changeIcon(binding.ivMenu2, R.drawable.ic_tiket)
                    changeIcon(binding.ivMenu3, R.drawable.ic_profile)
                    setFragment(fragmentHome)
                }
                2 -> {
                    changeIcon(binding.ivMenu1, R.drawable.ic_home)
                    changeIcon(binding.ivMenu2, R.drawable.ic_tiket_active)
                    changeIcon(binding.ivMenu3, R.drawable.ic_profile)
                    setFragment(fragmentTiket)
                }
                3 -> {
                    changeIcon(binding.ivMenu1, R.drawable.ic_home)
                    changeIcon(binding.ivMenu2, R.drawable.ic_tiket)
                    changeIcon(binding.ivMenu3, R.drawable.ic_profile_active)
                    setFragment(fragmentSetting)
                }
            }
        })
    }
}
