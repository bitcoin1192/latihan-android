package com.sisalma.movieticketapp.appActivity.buyTicket.seatSelectionFragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisalma.movieticketapp.ViewModelCinema
import com.sisalma.movieticketapp.appActivity.appHome.ViewModelNavTab
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.databinding.FragmentSeatSelectionBinding

class SeatSelectionFragment(): Fragment() {
    var _binding: FragmentSeatSelectionBinding? = null
    private val uiBind get() = _binding!!
    val adapter: seatSelectorAdapter = seatSelectorAdapter()
    val ViewModelCinema: ViewModelCinema by activityViewModels()
    val ViewModelNavTab: ViewModelNavTab by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSeatSelectionBinding.inflate(inflater,container,false)

        uiBind.tvKursi.text = ViewModelCinema.getNamaFilm()

        ViewModelCinema.getAvailableSeat().observe(this.viewLifecycleOwner, {
            Log.i("sometest","hey im here")
            adapter.setData(it)
            if(uiBind.rvSeatSelector.adapter == null){
                uiBind.rvSeatSelector.adapter = adapter
                uiBind.rvSeatSelector.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }else {
                adapter.notifyDataSetChanged()
            }
        })
        adapter.resultListener().observe(this.viewLifecycleOwner,{
            Log.i("some button is clicked","$it")
            ViewModelCinema.setSelectedSeat(it)
        })
        uiBind.imageView3.setOnClickListener {
            ViewModelNavTab.setCurrentPage(-1)
        }

        uiBind.btnTicketBuy.setOnClickListener {
            Log.i("ticketBuyBtn", ViewModelCinema.getSelectedSeat().size.toString())
            if(ViewModelCinema.getSelectedSeat().size != 0){
                ViewModelNavTab.setCurrentPage(1)
            }else{
                Toast.makeText(this.activity,"Pilih bangku terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
        return uiBind.root
    }
}