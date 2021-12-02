package com.sisalma.movieticketapp.appActivity.buyTicket.paymentConfirmFragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisalma.movieticketapp.appActivity.buyTicket.buyTicketActivity
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.dataUser
import com.sisalma.movieticketapp.databinding.FragmentPaymentConfirmBinding
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap

class paymentConfirmFragment(authUser: authenticatedUsers,filmName: String, seatResult: HashMap<String, Int>): Fragment() {
    var _binding: FragmentPaymentConfirmBinding? = null
    private val uiBind get() = _binding!!
    val authUser = authUser
    lateinit var userDetail: dataUser
    val seatResult = seatResult
    var total = 0
    lateinit var ticketActivity: buyTicketActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ticketActivity = activity as buyTicketActivity
        _binding = FragmentPaymentConfirmBinding.inflate(inflater,container,false)
        return uiBind.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        uiBind.rcCheckout.adapter = seatAdapter(seatResult)
        uiBind.rcCheckout.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        uiBind.btnCancel.setOnClickListener {
            activity?.finish()
        }
        uiBind.ivButtonBack.setOnClickListener {
            ticketActivity.backPage()
        }
        uiBind.btnConfirm.setOnClickListener {
            authUser
        }
        userDetail = authUser.getUserData()!!
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        uiBind.rcCheckout.adapter?.notifyDataSetChanged()
        uiBind.tvSaldo.text = calculateTotal(seatResult)
        uiBind.tvSisa.text = calculateTotal(userDetail.saldo)
        if(userDetail.saldo-total < 0){
            uiBind.btnConfirm.visibility = View.INVISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun calculateTotal(items: HashMap<String, Int>): String{
        val currency = NumberFormat.getCurrencyInstance(Locale("id","ID"))
        total = 0
        items.forEach { t, u ->
            total += u
        }
        return currency.format(total)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun calculateTotal(saldo: Int): String{
        val currency = NumberFormat.getCurrencyInstance(Locale("id","ID"))
        return currency.format((saldo-total))
    }
}