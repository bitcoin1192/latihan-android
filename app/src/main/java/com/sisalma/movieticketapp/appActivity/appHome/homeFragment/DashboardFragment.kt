package com.sisalma.movieticketapp.appActivity.appHome.homeFragment

import android.content.Intent
import android.icu.number.NumberFormatter
import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sisalma.movieticketapp.*
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.appActivity.filmDetailActivity
import com.sisalma.movieticketapp.databinding.FragmentDashboardBinding
import com.sisalma.movieticketapp.repository.filmRepository
import com.sisalma.movieticketapp.repository.userRepository
import java.util.*

class dashboardFragment(userRepository: userRepository, filmRepository: filmRepository) : Fragment() {
    var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    val userProfile = userRepository.getUserProfile()
    val filmRepo = filmRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFilm = viewModelFilm(filmRepo, this.viewLifecycleOwner)
        val filmList = viewModelFilm.getFilmData()

        userProfile.observe(this.viewLifecycleOwner, Observer {
            Log.e("userProfile change", "")
            binding.tvNama.text = (it.nama)
            binding.tvSaldo.text = convertInt2Rupiah(it.saldo)

            if(it.url != "" || it.url != "-") {
                Glide.with(binding.ivProfile)
                    .load(it.url).circleCrop()
                    .into(binding.ivProfile)
            }
        })

        filmList.observe(this.viewLifecycleOwner, Observer{
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
            binding.rvNowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvComingSoon.layoutManager = LinearLayoutManager(context)
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun convertInt2Rupiah(money: Int):String{
        val result = NumberFormat.getCurrencyInstance(Locale("id","ID"))
        return result.format(money)
    }
}