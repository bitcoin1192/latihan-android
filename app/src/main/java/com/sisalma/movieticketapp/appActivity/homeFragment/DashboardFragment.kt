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
import com.bagicode.bwamov.filmDetailActivity
import com.sisalma.movieticketapp.appActivity.homeFragment.ComingSoonAdapter
import com.sisalma.movieticketapp.appActivity.homeFragment.NowPlayingAdapter
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.databinding.FragmentDashboardBinding
import com.sisalma.movieticketapp.viewModelFilm

class dashboardFragment : Fragment() {
    var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFilm = viewModelFilm()
        val test = viewModelFilm.getFilmData()

        binding.tvNama.setText("Test 123")
        binding.tvSaldo.setText("Rp. 120.000")

        test.observe(this.viewLifecycleOwner, Observer{
            binding.rvNowPlaying.adapter = NowPlayingAdapter(it) {
                val intent = Intent(
                    context,
                    filmDetailActivity::class.java
                )
                startActivity(intent)
            }
            binding.rvComingSoon.adapter = ComingSoonAdapter(it) {
                val intent = Intent(
                    context,
                    filmDetailActivity::class.java
                )
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