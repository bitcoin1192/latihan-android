package com.sisalma.movieticketapp.appActivity.appHome.userProfileFragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.ViewModelUser
import com.sisalma.movieticketapp.appActivity.editProfile.editProfileActivity
import com.sisalma.movieticketapp.appActivity.saldoTopup.historiDompet
import com.sisalma.movieticketapp.dataUser
import com.sisalma.movieticketapp.databinding.FragmentSettingBinding
import com.sisalma.movieticketapp.onboarding.splash_screen
import com.sisalma.movieticketapp.repository.userRepository
import java.util.*

class settingFragment() : Fragment() {
    private lateinit var uiBind: FragmentSettingBinding
    val ViewModelUser: ViewModelUser by activityViewModels()
    lateinit var parcelableData: dataUser
    var instantiate = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        uiBind = FragmentSettingBinding.inflate(inflater,container,false)

        val setting = activity?.applicationContext?.getSharedPreferences("app-setting",MODE_PRIVATE)?.edit()!!

        ViewModelUser.getUserData().observe(this.viewLifecycleOwner,{
            uiBind.ivNama.text = it.nama
            uiBind.tvEmail.text = it.email
            Glide.with(uiBind.ivProfile)
                .load(it.url)
                .placeholder(R.drawable.ic_profile)
                .circleCrop()
                .into(uiBind.ivProfile)
            if(!instantiate){
                parcelableData = it
                uiBind.cardViewProfile.setOnClickListener {
                    val intent = Intent(this.activity, editProfileActivity::class.java)
                    startActivity(intent)
                }

                uiBind.cardViewLogout.setOnClickListener {
                    setting.remove("username")
                    setting.remove("password")
                    setting.remove("cleanAffinity")
                    setting.commit()
                    val intent = Intent(activity, splash_screen::class.java)

                    startActivity(intent)
                }

                uiBind.cardViewWallet.setOnClickListener {
                    val intent = Intent(this.activity,historiDompet::class.java).putExtra("saldoUser",parcelableData)
                    startActivity(intent)
                }
                instantiate = true
            }

        })

        return uiBind.root
    }
}