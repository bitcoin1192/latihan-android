package com.sisalma.movieticketapp.appActivity.buyTicket.seatSelectionFragment

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.dataStructure.seat

class seatSelectorButton(context: Context, attrs: AttributeSet): AppCompatImageView(context, attrs) {
    private var seatIsAvailable = false
    private var seatIsSelected = false
    private var _seatData = seat()

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.seatSelectorButton,
            0,0).apply {
            try {
                seatIsAvailable = getBoolean(R.styleable.seatSelectorButton_seatIsSelectable,false)
                seatIsSelected = getBoolean(R.styleable.seatSelectorButton_seatIsChecked, false)
            } finally {
                recycle()
            }
        }
    }

    fun isSeatSelectable():Boolean{
        return seatIsAvailable
    }

    fun isSeatSelected():Boolean{
        return seatIsSelected
    }

    fun setSeatData(input: seat){
        _seatData = input
        //setSeatIsSelectable(_seatData.statusAvailable)
        seatSelectToggle()
    }

    fun getSeatData(): seat{
        return _seatData
    }

    fun setSeatIsSelectable(selectable: Boolean){
        if(selectable) {
            setImageState(intArrayOf(R.attr.seatIsSelectable), true)
        }else{

        }
        seatIsAvailable = selectable
        invalidate()
        requestLayout()
    }
    fun seatSelectToggle(){
        if(_seatData.statusAvailable){
            if(_seatData.statusSelected){
                setImageState(intArrayOf(R.attr.seatIsChecked,R.attr.seatIsSelectable), false)
            }else{
                setImageState(intArrayOf(-R.attr.seatIsChecked,R.attr.seatIsSelectable), false)
            }
        }else{
            setImageState(intArrayOf(-R.attr.seatIsChecked,-R.attr.seatIsSelectable), false)
            Log.i("seatSelector-iv","Seat ${_seatData.seatID.toString()+_seatData.seatRow}is not available")
        }
        invalidate()
        requestLayout()
    }
}