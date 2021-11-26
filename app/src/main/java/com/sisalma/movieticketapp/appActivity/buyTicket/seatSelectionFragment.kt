package com.sisalma.movieticketapp.appActivity.buyTicket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.databinding.FragmentSeatSelectionBinding

class seatSelectionFragment(authUser: authenticatedUsers, filmName: String): Fragment() {
    var _binding: FragmentSeatSelectionBinding? = null
    val filmName = filmName
    private val uiBind get() = _binding!!
    val authUser = authUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeatSelectionBinding.inflate(inflater,container,false)
        return uiBind.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        uiBind.tvKursi.text = filmName
        val adapter = seatSelectorAdapter()
        uiBind.rvSeatSelector.adapter = adapter
        uiBind.rvSeatSelector.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        uiBind.imageView3.setOnClickListener {
            activity?.finish()
        }
        uiBind.btnTicketBuy.setOnClickListener {
            Log.i("ticketBuyBtn",adapter.seatResult.toString())
        }
    }
}