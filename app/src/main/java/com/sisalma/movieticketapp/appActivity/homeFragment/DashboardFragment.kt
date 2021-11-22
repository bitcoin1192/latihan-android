package com.sisalma.movieticketapp.appActivity.homeFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisalma.movieticketapp.*
import com.sisalma.movieticketapp.appActivity.Film
import com.sisalma.movieticketapp.appActivity.filmDetailActivity
import com.sisalma.movieticketapp.databinding.FragmentDashboardBinding

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFilm = viewModelFilm(filmRepo, this.viewLifecycleOwner)
        val filmList = viewModelFilm.getFilmData()

        userProfile.observe(this.viewLifecycleOwner, Observer {
            Log.e("userProfile change", "")
            binding.tvNama.setText(it.nama)
            binding.tvSaldo.setText(it.saldo)
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
}