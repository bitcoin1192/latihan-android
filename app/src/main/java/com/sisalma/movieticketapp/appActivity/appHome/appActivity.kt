package com.sisalma.movieticketapp.appActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.ViewModelUser
import com.sisalma.movieticketapp.appActivity.appHome.homeFragment.dashboardFragment
import com.sisalma.movieticketapp.appActivity.appHome.ticketFragment.ticketFragment
import com.sisalma.movieticketapp.appActivity.appHome.ticketFragment.ViewModelTicket
import com.sisalma.movieticketapp.appActivity.appHome.userProfileFragment.settingFragment
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.databinding.ActivityHomeBinding
import com.sisalma.movieticketapp.repository.filmRepository
import com.sisalma.movieticketapp.repository.userRepository
import com.sisalma.movieticketapp.viewModelFilm

class home : AppCompatActivity() {
    var currFragment: Fragment? = null
    val filmRepo = filmRepository()
    lateinit var userRepo: userRepository
    val viewModelFilm: viewModelFilm by viewModels()
    val viewModelUser: ViewModelUser by viewModels()
    val ViewModelTicket: ViewModelTicket by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settings = applicationContext.getSharedPreferences("app-setting", MODE_PRIVATE)
        val oldUser = settings.getBoolean("oldUser",false)

        val userObject = authenticatedUsers(applicationContext)
        userObject.userAuthenticate()
        userRepo = userRepository(userObject)
        observeRepository()

        val fragmentHome = dashboardFragment()
        val fragmentTiket = ticketFragment()
        val fragmentSetting = settingFragment()

        if(savedInstanceState == null){
            setFragment(fragmentHome)
        }

        binding.ivMenu1.setOnClickListener {
            setFragment(fragmentHome)

            changeIcon(binding.ivMenu1, R.drawable.ic_home_active)
            changeIcon(binding.ivMenu2, R.drawable.ic_tiket)
            changeIcon(binding.ivMenu3, R.drawable.ic_profile)
        }

        binding.ivMenu2.setOnClickListener {
            setFragment(fragmentTiket)

            changeIcon(binding.ivMenu1, R.drawable.ic_home)
            changeIcon(binding.ivMenu2, R.drawable.ic_tiket_active)
            changeIcon(binding.ivMenu3, R.drawable.ic_profile)
        }

        binding.ivMenu3.setOnClickListener {
            setFragment(fragmentSetting)

            changeIcon(binding.ivMenu1, R.drawable.ic_home)
            changeIcon(binding.ivMenu2, R.drawable.ic_tiket)
            changeIcon(binding.ivMenu3, R.drawable.ic_profile_active)
        }
        if(!oldUser) {
            settings.edit().putBoolean("oldUser", true).apply()
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if(currFragment != null) {
            if (fragment != currFragment) {
                fragmentTransaction.replace(R.id.layout_frame, fragment)
                fragmentTransaction.commit()
                currFragment = fragment
            }else{
                Log.i("setFragment","Not replacing fragment because user selected same menu")
            }
        }else{
            fragmentTransaction.replace(R.id.layout_frame, fragment)
            fragmentTransaction.commit()
            currFragment = fragment
        }
    }

    private fun changeIcon(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }

    private fun observeRepository(){
        filmRepo.getFilmLiveData().observe(this, {
            viewModelFilm.setFilmData(it)
            ViewModelTicket.setfilmList(it)
        })
        userRepo.getUserProfile().observe(this,{
            viewModelUser.setUserData(it)
        })
        userRepo.getUserTicket().observe(this,{
            ViewModelTicket.setDataTicket(it)
        })
    }
}
