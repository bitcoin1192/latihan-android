package com.sisalma.movieticketapp.appActivity.buyTicket.paymentConfirmFragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisalma.movieticketapp.ViewModelCinema
import com.sisalma.movieticketapp.ViewModelUser
import com.sisalma.movieticketapp.appActivity.appHome.ViewModelNavTab
import com.sisalma.movieticketapp.dataStructure.Seat
import com.sisalma.movieticketapp.databinding.FragmentPaymentConfirmBinding
import java.text.NumberFormat
import java.util.*

class paymentConfirmFragment : Fragment() {
    var _binding: FragmentPaymentConfirmBinding? = null
    private val uiBind get() = _binding!!

    var total = 0
    val ViewModelCinema: ViewModelCinema by activityViewModels()
    val ViewModelNavTab: ViewModelNavTab by activityViewModels()
    val ViewModelUser: ViewModelUser by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentConfirmBinding.inflate(inflater, container, false)
        ViewModelCinema.liveSelectedSeat().observe(this.viewLifecycleOwner,{
            uiBind.rcCheckout.adapter = SeatAdapter(it)
            uiBind.tvSaldo.text = calculateTotal(it)
            uiBind.tvSisa.text = calculateTotal(ViewModelUser.getUserData().saldo)
            if(ViewModelUser.getUserData().saldo - total < 0) {
                uiBind.btnConfirm.visibility = View.INVISIBLE
            } else {
                uiBind.btnConfirm.visibility = View.VISIBLE
            }
        })
        uiBind.rcCheckout.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        uiBind.btnCancel.setOnClickListener {
            activity?.finish()
        }
        uiBind.ivButtonBack.setOnClickListener {
            ViewModelNavTab.setCurrentPage(ViewModelNavTab.getCurrentPage() - 1)
        }
        uiBind.btnConfirm.setOnClickListener {
            ViewModelUser.buyTicket(ViewModelCinema.getSelectedSeat())
            ViewModelNavTab.setCurrentPage(ViewModelNavTab.getCurrentPage()+1)
        }
        return uiBind.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun calculateTotal(items: ArrayList<Seat>): String {
        val currency = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        total = 0
        items.forEach { u ->
            total += u.priceList
        }
        return currency.format(total)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun calculateTotal(saldo: Int): String {
        val currency = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return currency.format((saldo - total))
    }
}