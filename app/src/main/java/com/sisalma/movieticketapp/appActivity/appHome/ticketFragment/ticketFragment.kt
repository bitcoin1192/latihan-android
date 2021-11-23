package com.sisalma.movieticketapp.appActivity.appHome.ticketFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisalma.movieticketapp.appActivity.appHome.homeFragment.ComingSoonAdapter
import com.sisalma.movieticketapp.databinding.FragmentTicketBinding
import com.sisalma.movieticketapp.repository.filmRepository
import com.sisalma.movieticketapp.repository.userRepository

class ticketFragment (userRepository: userRepository, filmRepository: filmRepository): Fragment() {
    val userRepo = userRepository
    val filmRepo = filmRepository
    var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTicketBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("ticketFragment", "activity created")
        val viewModel = viewTicketModel(userRepo,filmRepo,this.viewLifecycleOwner)
        val data = viewModel.getUserActiveTicket()
        data.observe(this.viewLifecycleOwner, Observer{
            binding.rvTicketList.adapter = ComingSoonAdapter(it){ data ->
                Log.i("ticketFragment", data.judul)
            }

            binding.rvTicketList.layoutManager = LinearLayoutManager(context)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}