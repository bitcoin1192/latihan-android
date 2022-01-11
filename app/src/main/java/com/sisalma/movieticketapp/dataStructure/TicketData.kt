package com.sisalma.movieticketapp.dataStructure

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ticketData(
    val expDate: String = "",
    val qrData: String = "",
    val namaFilm: String = "",
    val seatPrice: Int = 0,
    val selectedSeat: ArrayList<seat> = ArrayList()
):Parcelable