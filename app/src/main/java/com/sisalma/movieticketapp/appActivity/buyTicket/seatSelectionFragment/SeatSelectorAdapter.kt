package com.sisalma.movieticketapp.appActivity.buyTicket.seatSelectionFragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.sisalma.movieticketapp.dataStructure.seat
import com.sisalma.movieticketapp.dataStructure.sesiTayang
import com.sisalma.movieticketapp.databinding.RowSeatBinding

class seatSelectorAdapter : RecyclerView.Adapter<seatSelection>() {
    private lateinit var _cinemaSession: sesiTayang
    var seatLevel: ArrayList<String> = ArrayList()

    private var _seatResult: MutableLiveData<seat> = MutableLiveData()
    private var _seatDataGroup: HashMap<String, ArrayList<seat>> = HashMap()
    lateinit var ContextAdapter: Context

    private var _binding: RowSeatBinding? = null
    private val uiBind get() = _binding!!

    override fun getItemCount(): Int {
        return seatLevel.size
    }

    override fun onBindViewHolder(holder: seatSelection, position: Int) {
        _seatDataGroup.get(seatLevel[position])?.let { holder.setRowData(it) }
        holder.bindSeat(seatLevel[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): seatSelection {
        Log.e("seatSelection", "Its alive")

        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        _binding = RowSeatBinding.inflate(layoutInflater, parent, false)

        return seatSelection(uiBind, _seatResult)
    }

    fun setData(input: sesiTayang) {
        _cinemaSession = input
        countSeatLevel()
        groupSeatData()
    }

    fun resultListener(): LiveData<seat> {
        return _seatResult
    }

    private fun countSeatLevel() {
        seatLevel.clear()
        _cinemaSession.availableSeat.forEachIndexed { i, seatDetail ->
            if(seatLevel.size == 0) {
                seatLevel.add(seatDetail.seatRow.uppercase())
            }
            if(seatDetail.seatRow.lowercase() != seatLevel[seatLevel.size - 1].lowercase()) {
                seatLevel.add(seatDetail.seatRow.uppercase())
                Log.i("test-byte", "$seatLevel + $i")
            }
        }
    }

    private fun groupSeatData() {
        seatLevel.forEach { level ->
            _seatDataGroup.put(
                level, ArrayList(_cinemaSession.availableSeat.filter {
                    it.seatRow.lowercase() == level.lowercase()
                })
            )
        }

    }
}

class seatSelection(view: RowSeatBinding, val _resultPipe: MutableLiveData<seat>) :
    RecyclerView.ViewHolder(view.root) {
    private var _seatRowData: ArrayList<seat> = ArrayList()
    private val _uiBind = view
    private val rowSeat = arrayListOf<seatSelectorButton>(
        _uiBind.seatSelector1, _uiBind.seatSelector2,
        _uiBind.seatSelector3, _uiBind.seatSelector4
    )

    fun bindSeat(seatLevelName: String) {
        _uiBind.tvRowIndicator.text = seatLevelName.uppercase()
        // Iterate over array of seatSelectorButton and
        // then set the onClickListener to respond to user input
        rowSeat.forEachIndexed { index, seatSelectorButton ->
            seatSelectorButton.setSeatData(_seatRowData[index])
            //Listen event from seatSelector only
            //if upstream data permit it
            if(_seatRowData[index].statusAvailable) {
                seatSelectorButton.setOnClickListener {
                    _resultPipe.value = seatSelectorButton.getSeatData()
                }
            }
        }
    }

    fun setRowData(input: ArrayList<seat>) {
        _seatRowData = input
    }
}