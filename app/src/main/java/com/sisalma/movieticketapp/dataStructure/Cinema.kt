package com.sisalma.movieticketapp.dataStructure

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class cinemaDetail(
    val namaCinema: String = "",
    val Lokasi: String = ""
)

data class sesiTayang(
    val namaFilm: String = "",
    val ruangTayang: Int = 1,
    val tanggalTayang: String = "",
    val priceList: Int = 0,
    val availableSeat: ArrayList<seat> = ArrayList()
)

@Parcelize
data class seat(
    var seatRow: String = "",
    var seatID: Int = 0,
    var statusAvailable: Boolean = true,
    var statusSelected: Boolean = false,
    var priceList: Int = 0
):Parcelable