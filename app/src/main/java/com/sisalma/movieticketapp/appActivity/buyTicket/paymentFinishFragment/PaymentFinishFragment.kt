package com.sisalma.movieticketapp.appActivity.buyTicket.paymentFinishFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sisalma.movieticketapp.appActivity.appHome.ViewModelNavTab
import com.sisalma.movieticketapp.databinding.FragmentPaymentFinishBinding

class paymentFinishFragment(): Fragment() {
    var _binding: FragmentPaymentFinishBinding? = null
    private val uiBind get() = _binding!!
    private val ViewModelNavTab: ViewModelNavTab by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentFinishBinding.inflate(inflater,container,false)
        return uiBind.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        uiBind.btnHome.setOnClickListener {
            ViewModelNavTab.setCurrentPage(ViewModelNavTab.getCurrentPage()+1)
        }
    }
}