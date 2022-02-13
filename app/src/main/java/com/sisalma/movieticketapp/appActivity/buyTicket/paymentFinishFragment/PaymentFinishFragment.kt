package com.sisalma.movieticketapp.appActivity.buyTicket.paymentFinishFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sisalma.movieticketapp.appActivity.appHome.TicketShowActivity
import com.sisalma.movieticketapp.appActivity.appHome.ViewModelNavTab
import com.sisalma.movieticketapp.appActivity.appHome.ticketFragment.ViewModelTicket
import com.sisalma.movieticketapp.databinding.FragmentPaymentFinishBinding

class paymentFinishFragment : Fragment() {
    var _binding: FragmentPaymentFinishBinding? = null
    private val uiBind get() = _binding!!
    private val ViewModelNavTab: ViewModelNavTab by activityViewModels()
    private val ViewModelTicket: ViewModelTicket by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentFinishBinding.inflate(inflater, container, false)
        uiBind.btnHome.setOnClickListener {
            ViewModelNavTab.setCurrentPage(ViewModelNavTab.getCurrentPage() + 1)
        }
        uiBind.btnTiket.setOnClickListener {
            val intent = Intent(this.activity, TicketShowActivity::class.java)
                .putExtra("filmDetail", ViewModelTicket.filmList[0])
                .putExtra("ticketData", ViewModelTicket.ticketArrayList[0])
            startActivity(intent)
        }
        return uiBind.root
    }
}