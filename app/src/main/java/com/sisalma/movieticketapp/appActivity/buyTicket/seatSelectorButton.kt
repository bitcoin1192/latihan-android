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
        seatIsAvailable = selectable
        invalidate()
        requestLayout()
    }
    fun setSeatSelected(selectable: Boolean){
        Log.i("extend-iv","setSeatSelected is ${drawableState[0]}")
        setImageState(intArrayOf(R.attr.seatIsSelectable), true)
        seatIsSelected = selectable
        invalidate()
        requestLayout()
    }
}