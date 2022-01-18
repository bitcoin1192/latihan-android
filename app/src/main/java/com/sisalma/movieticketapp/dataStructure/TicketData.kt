package com.sisalma.movieticketapp.dataStructure

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ticketData(
    val UID: String = "",
    val expDate: String = "",
    val qrData: String = "",
    val namaFilm: String = "",
    var seatPrice: Int = 0,
    val selectedSeat: ArrayList<seat> = ArrayList()
):Parcelable