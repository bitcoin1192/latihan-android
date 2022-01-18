package com.sisalma.movieticketapp.appActivity.appHome.homeFragment

import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sisalma.movieticketapp.*
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.appActivity.filmDetailActivity
import com.sisalma.movieticketapp.databinding.FragmentDashboardBinding
import java.util.*

//class dashboardFragment(userRepository: userRepository, filmRepository: filmRepository) : Fragment() {
class dashboardFragment() : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    //val userProfile = userRepository.getUserProfile()
    //val filmRepo = filmRepository
    private val viewModelFilm: viewModelFilm by activityViewModels()
    private val ViewModelUser: ViewModelUser by activityViewModels()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater,container,false)

        ViewModelUser.liveUserData().observe(this.viewLifecycleOwner, Observer {
            Log.e("userProfile change", "")
            binding.tvNama.text = (it.nama)
            binding.tvSaldo.text = convertInt2Rupiah(it.saldo)

            Glide.with(binding.ivProfile)
                .load(it.url)
                .placeholder(R.drawable.defaultprofilepic)
                .circleCrop()
                .into(binding.ivProfile)
        })

        viewModelFilm.getFilmData().observe(viewLifecycleOwner, {
            if(binding.rvComingSoon.adapter == null || binding.rvNowPlaying.adapter == null){
                binding.rvNowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.rvComingSoon.layoutManager = LinearLayoutManager(context)
            }
            binding.rvNowPlaying.adapter = NowPlayingAdapter(it) { data: Film ->
                val intent = Intent(
                    activity,
                    filmDetailActivity::class.java
                ).putExtra("filmDetail",data)
                startActivity(intent)
            }
            binding.rvComingSoon.adapter = ComingSoonAdapter(it) { data: Film ->
                val intent = Intent(
                    activity,
                    filmDetailActivity::class.java
                ).putExtra("filmDetail",data)
                startActivity(intent)
            }
            Log.i("viewModelObserver-db",it.toString())
        })
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun convertInt2Rupiah(money: Int):String{
        val result = NumberFormat.getCurrencyInstance(Locale("id","ID"))
        return result.format(money)
    }
}