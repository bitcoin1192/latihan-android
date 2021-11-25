package com.sisalma.movieticketapp.appActivity.buyTicket

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sisalma.movieticketapp.databinding.RowSeatBinding

class seatSelectorAdapter: RecyclerView.Adapter<seatSelectorAdapter.seatSelection>() {
    val seatLevel = arrayListOf<String>("A","B","C","D")
    lateinit var ContextAdapter: Context
    var _binding: RowSeatBinding? = null
    private val uiBind get() = _binding!!

    override fun getItemCount(): Int {
        return 4 // ABCD Level
    }

    override fun onBindViewHolder(holder: seatSelection, position: Int) {
        holder.bindSeat(seatLevel[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): seatSelection {
        Log.e("seatSelection","Its alive")

        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        var uiBind = RowSeatBinding.inflate(layoutInflater,parent,false)

        return seatSelectorAdapter.seatSelection(uiBind)
    }
    class seatSelection(view: RowSeatBinding):RecyclerView.ViewHolder(view.root){
        val uiBind = view
        var result = HashMap<String, Boolean>()
        fun bindSeat(seatLevelName: String){
            uiBind.tvRowIndicator.text = seatLevelName
            uiBind.seatSelector1.setOnClickListener {
                if(result.containsKey(seatLevelName+"1")){
                    result.remove(seatLevelName+"1")
                    uiBind.seatSelector1.setSeatSelected(false)
                }else {
                    result.put(seatLevelName+"1", true)
                    uiBind.seatSelector1.setSeatSelected(true)
                }
            }
            uiBind.seatSelector2.setOnClickListener {
                if(result.containsKey(seatLevelName+"2")){
                    result.remove(seatLevelName+"2")
                }else {
                    result.put(seatLevelName+"2", true)
                }
            }
            uiBind.seatSelector3.setOnClickListener {
                if(result.containsKey(seatLevelName+"3")){
                    result.remove(seatLevelName+"3")
                }else {
                    result.put(seatLevelName+"3", true)
                }
            }
            uiBind.seatSelector4.setOnClickListener {
                if(result.containsKey(seatLevelName+"4")){
                    result.remove(seatLevelName+"4")
                }else {
                    result.put(seatLevelName+"4", true)
                }
            }
        }
    }
}