package com.sisalma.movieticketapp.appActivity.buyTicket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.databinding.FragmentPaymentConfirmBinding

class paymentConfirmFragment(authUser: authenticatedUsers,filmName: String): Fragment() {
    var _binding: FragmentPaymentConfirmBinding? = null
    private val uiBind get() = _binding!!
    val authUser = authUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentConfirmBinding.inflate(inflater,container,false)
        return uiBind.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        uiBind.btnCancel.setOnClickListener {
            activity?.finish()
        }
    }
}