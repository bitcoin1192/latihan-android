package com.sisalma.movieticketapp.appActivity.buyTicket.seatSelectionFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisalma.movieticketapp.appActivity.buyTicket.buyTicketActivity
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.databinding.FragmentSeatSelectionBinding
import com.sisalma.movieticketapp.repository.cinemaRepository

class seatSelectionFragment(filmObj: Film, cinemaRepository: cinemaRepository): Fragment() {
    val filmDetail = filmObj
    val seatAvailability = cinemaRepository.getSeatAvailability()

    var _binding: FragmentSeatSelectionBinding? = null
    private val uiBind get() = _binding!!
    lateinit var ticketActivity: buyTicketActivity
    val adapter: seatSelectorAdapter = seatSelectorAdapter(filmDetail.priceList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val seatData = seatViewModel()
        val seatArray = seatData.getSeatStatus()

        ticketActivity = activity as buyTicketActivity
        _binding = FragmentSeatSelectionBinding.inflate(inflater,container,false)

        uiBind.tvKursi.text = filmDetail.judul

        seatArray.observe(this.viewLifecycleOwner, Observer {
            adapter.setData(it)
            if(uiBind.rvSeatSelector.adapter == null){
                Log.i("adapterAttach", "Check 1")
                uiBind.rvSeatSelector.adapter = adapter
                uiBind.rvSeatSelector.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }else {
                adapter.notifyDataSetChanged()
            }
        })

        uiBind.imageView3.setOnClickListener {
            ticketActivity.backPage()
        }

        uiBind.btnTicketBuy.setOnClickListener {
            Log.i("ticketBuyBtn",adapter.seatResult.size.toString())
            if(adapter.seatResult.size != 0){
                ticketActivity.nextPage()
            }else{
                Toast.makeText(this.activity,"Pilih bangku terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
        return uiBind.root
    }

    fun returnSeatResult(): HashMap<String,Int>{
        return adapter.seatResult
    }
}