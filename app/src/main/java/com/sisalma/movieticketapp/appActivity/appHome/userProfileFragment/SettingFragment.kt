package com.sisalma.movieticketapp.appActivity.appHome.userProfileFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.ViewModelUser
import com.sisalma.movieticketapp.appActivity.appHome.ticketFragment.ViewModelTicket
import com.sisalma.movieticketapp.appActivity.editProfile.EditProfileActivity
import com.sisalma.movieticketapp.appActivity.saldoTopup.historiDompet
import com.sisalma.movieticketapp.dataUser
import com.sisalma.movieticketapp.databinding.FragmentSettingBinding
import com.sisalma.movieticketapp.onboarding.SplashScreen

class SettingFragment : Fragment() {
    private lateinit var uiBind: FragmentSettingBinding
    private val ViewModelUser: ViewModelUser by activityViewModels()
    private val ViewModelTicket: ViewModelTicket by activityViewModels()
    lateinit var parcelableData: dataUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        uiBind = FragmentSettingBinding.inflate(inflater, container, false)

        ViewModelUser.liveUserData().observe(this.viewLifecycleOwner, { userData ->
            uiBind.ivNama.text = userData.nama
            uiBind.tvEmail.text = userData.email
            Glide.with(uiBind.ivProfile)
                .load(userData.url)
                .placeholder(R.drawable.ic_profile)
                .circleCrop()
                .into(uiBind.ivProfile)
            parcelableData = userData
        }
        )
        uiBind.cardViewProfile.setOnClickListener {
            val intent = Intent(this.activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        uiBind.cardViewLogout.setOnClickListener {
            val intent = Intent(activity, SplashScreen::class.java)
            ViewModelUser.userLogout(true)
            startActivity(intent)
        }

        uiBind.cardViewWallet.setOnClickListener {
            val intent = Intent(this.activity, historiDompet::class.java)
                .putExtra("saldoUser", parcelableData)
                .putParcelableArrayListExtra("ticketData", ViewModelTicket.ticketArrayList)
            startActivity(intent)
        }

        return uiBind.root
    }
}