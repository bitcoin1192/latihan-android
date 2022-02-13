package com.sisalma.movieticketapp.appActivity.buyTicket.paymentConfirmFragment

import android.content.Context
import android.icu.text.NumberFormat
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.sisalma.movieticketapp.dataStructure.Seat
import com.sisalma.movieticketapp.databinding.RowItemCheckoutBinding
import java.util.*

open class SeatAdapter(seatResult: ArrayList<Seat>) : RecyclerView.Adapter<SeatAdapter.itemView>() {
    lateinit var ContextAdapter: Context
    private val seatResultMap = seatResult
    private var seatResultArray = arrayListOf<Seat>()
    var _binding: RowItemCheckoutBinding? = null
    val uiBind get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemView {
        getMapItem()
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        _binding = RowItemCheckoutBinding.inflate(layoutInflater, parent, false)
        return itemView(uiBind)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: itemView, position: Int) {
        holder.bindMap(seatResultArray[position])
    }

    override fun getItemCount(): Int {
        return seatResultMap.size
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getMapItem() {
        seatResultArray.clear()
        for (entry in seatResultMap) {
            seatResultArray.add(entry)
        }
        Log.i("seatTotal", seatResultArray.size.toString())
    }

    class itemView(view: RowItemCheckoutBinding) : RecyclerView.ViewHolder(view.root) {
        private val uiBind = view

        @RequiresApi(Build.VERSION_CODES.N)
        fun bindMap(seatResult: Seat) {
            uiBind.tvKursi.text = "Seat " + seatResult.seatRow + seatResult.seatID
            val Int2Rupiah = NumberFormat
                .getCurrencyInstance(Locale("id", "ID"))
                .format(seatResult.priceList)
            if(Int2Rupiah == "Rp 0,00") {
                uiBind.tvHarga.text = ""
            } else {
                uiBind.tvHarga.text = Int2Rupiah
            }
        }
    }
}

class SeatAdapterTheme(seatResult: ArrayList<Seat>, textColor: Int) : SeatAdapter(seatResult) {
    val textColor = textColor

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemView {
        getMapItem()
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        _binding = RowItemCheckoutBinding.inflate(layoutInflater, parent, false)
        textColorContrast()
        return itemView(uiBind)
    }

    private fun textColorContrast() {
        uiBind.tvKursi.setTextColor(textColor)
    }
}
