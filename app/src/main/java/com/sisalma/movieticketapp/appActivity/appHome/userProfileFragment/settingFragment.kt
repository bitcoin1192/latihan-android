package com.sisalma.movieticketapp.appActivity.appHome.userProfileFragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sisalma.movieticketapp.appActivity.editProfile.editProfileActivity
import com.sisalma.movieticketapp.databinding.FragmentSettingBinding
import com.sisalma.movieticketapp.onboarding.splash_screen
import com.sisalma.movieticketapp.repository.userRepository

class settingFragment(userRepository: userRepository) : Fragment() {
    var _binding: FragmentSettingBinding? = null
    private val uiBind get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater,container,false)
        return uiBind.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val setting = activity?.applicationContext?.getSharedPreferences("app-setting",MODE_PRIVATE)?.edit()!!

        uiBind.cardViewProfile.setOnClickListener {
            val intent = Intent(this.activity, editProfileActivity::class.java)
            startActivity(intent)
        }

        uiBind.cardViewLogout.setOnClickListener {
            setting.remove("username")
            setting.remove("password")
            setting.commit()
            val intent = Intent(activity, splash_screen::class.java)
            startActivity(intent)
        }
    }

}