package com.sisalma.movieticketapp.appActivity.buyTicket.paymentConfirmFragment

import android.content.Context
import android.graphics.Color
import android.icu.text.NumberFormat
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.appActivity.buyTicket.seatSelectionFragment.seatSelectorAdapter
import com.sisalma.movieticketapp.databinding.RowItemCheckoutBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class seatAdapter(seatResult: HashMap<String,Int>): RecyclerView.Adapter<seatAdapter.itemView>() {
    lateinit var ContextAdapter: Context
    val seatResultMap = seatResult
    private var seatResultArray = arrayListOf<itemData>()
    var _binding: RowItemCheckoutBinding? = null
    val uiBind get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemView {
        getMapItem()
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        _binding = RowItemCheckoutBinding.inflate(layoutInflater,parent,false)
        return itemView(uiBind)
    }

    override fun onBindViewHolder(holder: itemView, position: Int) {
        holder.bindMap(seatResultArray[position])
    }

    override fun getItemCount(): Int {
        return seatResultMap.size
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getMapItem(){
        seatResultArray.clear()
        val NumberFormat = NumberFormat.getCurrencyInstance(
            Locale("id","ID"))
        for (entry in seatResultMap) {
            seatResultArray.add(itemData(entry.key,NumberFormat.format(entry.value)))
        }
        Log.i("seatTotal", seatResultArray.size.toString())
    }
    class itemView(view: RowItemCheckoutBinding): RecyclerView.ViewHolder(view.root){
        private val uiBind = view
        fun bindMap(seatResult: itemData){
            uiBind.tvKursi.text = "Seat "+seatResult.kursi
            if(seatResult.harga == "Rp 0,00") {
                uiBind.tvHarga.text = ""
            }else{
                uiBind.tvHarga.text = seatResult.harga
            }
        }
    }
}

class SeatAdapterTheme(seatResult: HashMap<String,Int>, textColor: Int): seatAdapter(seatResult){
    val textColor = textColor

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemView {
        getMapItem()
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        _binding = RowItemCheckoutBinding.inflate(layoutInflater,parent,false)
        textColorContrast()
        return itemView(uiBind)
    }

    private fun textColorContrast(){
        uiBind.tvKursi.setTextColor(textColor)
    }
}

data class itemData(val kursi: String = "",
                    val harga: String = "")