package com.sisalma.movieticketapp.appActivity.buyTicket

import android.animation.StateListAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.sisalma.movieticketapp.R

class seatSelectorButton(context: Context, attrs: AttributeSet): AppCompatImageView(context, attrs) {
    var seatIsAvailable = false
    var seatIsSelected = false
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
    fun setSeatIsSelectable(selectable: Boolean){
        if(selectable) {
            setImageState(intArrayOf(R.attr.seatIsSelectable), true)
        }else{
            setImageState(intArrayOf(-R.attr.seatIsSelectable), true)
        }
        seatIsAvailable = selectable
        invalidate()
        requestLayout()
    }
    fun seatSelectToggle(){
        if(seatIsAvailable){
            if(seatIsSelected){
                setImageState(intArrayOf(-R.attr.seatIsChecked,R.attr.seatIsSelectable), true)
            }else{
                setImageState(intArrayOf(R.attr.seatIsChecked,R.attr.seatIsSelectable), true)
            }
        }else{
            Log.i("extend-iv","Selected seat is not available")
        }
        seatIsSelected = !seatIsSelected
        Log.i("extend-iv","setSeatSelected is now $seatIsSelected")
        invalidate()
        requestLayout()
    }
}