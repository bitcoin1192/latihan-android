package com.sisalma.movieticketapp.appActivity.buyTicket.seatSelectionFragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sisalma.movieticketapp.databinding.RowSeatBinding

class seatSelectorAdapter(): RecyclerView.Adapter<seatSelectorAdapter.seatSelection>() {
    val seatLevel = arrayListOf<String>("A","B","C","D")
    var seatStatus = ArrayList<ArrayList<Boolean>>()
    val seatResult = HashMap<String,Boolean>()
    lateinit var ContextAdapter: Context

    var _binding: RowSeatBinding? = null
    private val uiBind get() = _binding!!

    override fun getItemCount(): Int {
        return seatLevel.size
    }

    override fun onBindViewHolder(holder: seatSelection, position: Int) {
        holder.setAvailableSeat(seatStatus[position])
        holder.bindSeat(seatLevel[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): seatSelection {
        Log.e("seatSelection","Its alive")

        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        var uiBind = RowSeatBinding.inflate(layoutInflater,parent,false)

        return seatSelection(uiBind, seatResult)
    }

    fun setData(seatData: ArrayList<ArrayList<Boolean>>){
        seatStatus = seatData
    }

    class seatSelection(view: RowSeatBinding, seatResult: HashMap<String, Boolean>):RecyclerView.ViewHolder(view.root){
        var seatAvailability: ArrayList<Boolean> = ArrayList()
        private val uiBind = view
        val result = seatResult
        private val rowSeat = arrayListOf<seatSelectorButton>(uiBind.seatSelector1,uiBind.seatSelector2,
                                    uiBind.seatSelector3,uiBind.seatSelector4)

        fun bindSeat(seatLevelName: String){
            selectAvailableSeat()
            uiBind.tvRowIndicator.text = seatLevelName
            // Iterate over array of seatSelectorButton and
            // then set the onClickListener to respond to user input
            rowSeat.forEachIndexed { index, seatSelectorButton ->
                seatSelectorButton.setOnClickListener {
                    val seatName = seatLevelName+index.toString()
                    if(result.containsKey(seatName)) {
                        result.remove(seatName)
                    }
                    else {
                        result.put(seatName, true)
                    }
                    seatSelectorButton.seatSelectToggle()
                }
            }
        }

        fun selectAvailableSeat(){
            rowSeat.forEachIndexed { index, seatSelectorButton ->
                Log.i("seatIndex", seatAvailability.size.toString())
                if(seatAvailability[index]){
                    seatSelectorButton.setSeatIsSelectable(true)
                }else{
                    seatSelectorButton.setSeatIsSelectable(false)
                }
            }
        }

        fun setAvailableSeat(seatStatus: ArrayList<Boolean>){
            seatAvailability = seatStatus
        }
    }
}