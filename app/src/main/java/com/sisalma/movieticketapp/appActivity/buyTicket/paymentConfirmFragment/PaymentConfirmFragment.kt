package com.sisalma.movieticketapp.appActivity.buyTicket.paymentConfirmFragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisalma.movieticketapp.ViewModelCinema
import com.sisalma.movieticketapp.ViewModelUser
import com.sisalma.movieticketapp.appActivity.appHome.ViewModelNavTab
import com.sisalma.movieticketapp.dataStructure.seat
import com.sisalma.movieticketapp.databinding.FragmentPaymentConfirmBinding
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class paymentConfirmFragment(): Fragment() {
    var _binding: FragmentPaymentConfirmBinding? = null
    private val uiBind get() = _binding!!

    var total = 0
    val ViewModelCinema: ViewModelCinema by activityViewModels()
    val ViewModelNavTab: ViewModelNavTab by activityViewModels()
    val ViewModelUser: ViewModelUser by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPaymentConfirmBinding.inflate(inflater,container,false)
        //uiBind.rcCheckout.adapter = seatAdapter(seatResult)
        uiBind.rcCheckout.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        uiBind.btnCancel.setOnClickListener {
            activity?.finish()
        }
        uiBind.ivButtonBack.setOnClickListener {
            ViewModelNavTab.setCurrentPage(ViewModelNavTab.getCurrentPage()-1)
        }
        uiBind.btnConfirm.setOnClickListener {
            ViewModelUser.buyTicket(ViewModelCinema.getSelectedSeat())
            //authUser.buyTicket(filmName, sbuild)
            //authUser.potongSaldo(total)
            ViewModelNavTab.setCurrentPage(2)
        }
        //userDetail = authUser.getUserData()!!
        return uiBind.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        uiBind.rcCheckout.adapter?.notifyDataSetChanged()
        uiBind.tvSaldo.text = calculateTotal(ViewModelCinema.getSelectedSeat())
        uiBind.tvSisa.text = calculateTotal(ViewModelUser.getUserData().saldo)
        if(ViewModelUser.getUserData().saldo-total < 0){
            uiBind.btnConfirm.visibility = View.INVISIBLE
        }else{
            uiBind.btnConfirm.visibility = View.VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun calculateTotal(items: ArrayList<seat>): String{
        val currency = NumberFormat.getCurrencyInstance(Locale("id","ID"))
        total = 0
        items.forEach { u ->
            total += u.priceList
        }
        return currency.format(total)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun calculateTotal(saldo: Int): String{
        val currency = NumberFormat.getCurrencyInstance(Locale("id","ID"))
        return currency.format((saldo-total))
    }
}