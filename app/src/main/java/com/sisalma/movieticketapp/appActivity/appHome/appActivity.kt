package com.sisalma.movieticketapp.appActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.appActivity.appHome.homeFragment.dashboardFragment
import com.sisalma.movieticketapp.appActivity.appHome.ticketFragment.ticketFragment
import com.sisalma.movieticketapp.appActivity.appHome.userProfileFragment.settingFragment
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.databinding.ActivityHomeBinding
import com.sisalma.movieticketapp.repository.filmRepository
import com.sisalma.movieticketapp.repository.userRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class home : AppCompatActivity() {
    var currFragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settings = applicationContext.getSharedPreferences("app-setting", MODE_PRIVATE)
        val name = settings.getString("username","")?:""
        val pass = settings.getString("password","")?:""
        val oldUser = settings.getBoolean("oldUser",false)

        val userObject = authenticatedUsers()
        userObject.userAuthenticate(name,pass)

        val userCheckCoroutine = CoroutineScope(Dispatchers.Main)
        userCheckCoroutine.launch {
            var result = async { userObject.testAuthorizeUser(userObject) }
            if (result.await()) {
                val filmRepo = filmRepository()
                val userRepo = userRepository(userObject)
                val fragmentTiket = ticketFragment(userRepo,filmRepo)
                val fragmentSetting = settingFragment(userRepo)
                val fragmentHome = dashboardFragment(userRepo,filmRepo)

                setFragment(fragmentHome)
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
                if (!oldUser){
                    settings.edit().putBoolean("oldUser", true).apply()
                }
            }
        }
    }


    protected fun setFragment(fragment: Fragment) {
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
}
