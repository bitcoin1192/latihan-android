package com.sisalma.movieticketapp.appActivity.saldoTopup

import android.content.Context
import android.icu.text.NumberFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.sisalma.movieticketapp.dataStructure.ticketData
import com.sisalma.movieticketapp.databinding.RowItemTransaksiBinding
import java.util.*

class rowTransactionAdapter(inputData: ArrayList<ticketData>) :
    RecyclerView.Adapter<rowTransactionAdapter.rowItem>() {
    val ticketArray = inputData
    var _binding: RowItemTransaksiBinding? = null
    val uiBind get() = _binding!!
    lateinit var ContextAdapter: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rowItem {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        _binding = RowItemTransaksiBinding.inflate(layoutInflater, parent, false)
        return rowItem(uiBind)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: rowItem, position: Int) {
        holder.bindItem(ticketArray[position])
    }

    override fun getItemCount(): Int {
        return ticketArray.size
    }

    class rowItem(view: RowItemTransaksiBinding) : RecyclerView.ViewHolder(view.root) {
        private val uiBind = view

        @RequiresApi(Build.VERSION_CODES.N)
        fun bindItem(ticket: ticketData) {
            val Int2Rupiah = NumberFormat
                .getCurrencyInstance(Locale("id", "ID"))
                .format(ticket.seatPrice)
            uiBind.tvDate.text = ticket.expDate
            uiBind.tvMoney.text = Int2Rupiah
            uiBind.tvMovie.text = ticket.namaFilm
        }
    }
}