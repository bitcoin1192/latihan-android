package com.sisalma.movieticketapp.appActivity.appHome.ticketFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisalma.movieticketapp.appActivity.appHome.homeFragment.ticketRowAdapter
import com.sisalma.movieticketapp.appActivity.appHome.ticketShowActivity
import com.sisalma.movieticketapp.databinding.FragmentTicketBinding

class ticketFragment (): Fragment() {
    private val ViewModelTicket: ViewModelTicket by activityViewModels()
    var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTicketBinding.inflate(inflater,container,false)
        Log.i("ticketFragment", "activity created")

        ViewModelTicket.availableTicketFilms.observe(this.viewLifecycleOwner, Observer{
            binding.rvTicketList.adapter = ticketRowAdapter(ViewModelTicket.getFilmMapData(),it){ data, ticket ->
                Log.i("ticketFragment", data.judul)
                val intent = Intent(this.activity,ticketShowActivity::class.java)
                    .putExtra("filmDetail",data)
                    .putExtra("ticketData", ticket)
                Log.i("getsomedata",ViewModelTicket.getTicketData(data.judul).toString())
                startActivity(intent)
            }

            binding.rvTicketList.layoutManager = LinearLayoutManager(context)
        })
        return binding.root
    }
}