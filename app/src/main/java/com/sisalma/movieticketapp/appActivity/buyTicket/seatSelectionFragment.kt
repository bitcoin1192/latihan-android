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

class seatSelectionFragment(filmName: String): Fragment() {
    val filmName = filmName
    var _binding: FragmentSeatSelectionBinding? = null
    private val uiBind get() = _binding!!
    lateinit var ticketActivity:buyTicketActivity
    lateinit var adapter:seatSelectorAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ticketActivity = activity as buyTicketActivity
        _binding = FragmentSeatSelectionBinding.inflate(inflater,container,false)
        return uiBind.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        uiBind.tvKursi.text = filmName
        adapter = seatSelectorAdapter()
        uiBind.rvSeatSelector.adapter = adapter
        uiBind.rvSeatSelector.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        uiBind.imageView3.setOnClickListener {
            ticketActivity.backPage()
        }

        uiBind.btnTicketBuy.setOnClickListener {
            Log.i("ticketBuyBtn",adapter.seatResult.toString())
            ticketActivity.nextPage()
        }
    }

    fun returnSeatResult(): HashMap<String,Boolean>{
        return adapter.seatResult
    }
}