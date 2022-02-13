package com.sisalma.movieticketapp.dataStructure

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CinemaDetail(
    val namaCinema: String = "",
    val Lokasi: String = ""
)

data class SesiTayang(
    val namaFilm: String = "",
    val ruangTayang: Int = 1,
    val tanggalTayang: String = "",
    val priceList: Int = 0,
    val availableSeat: ArrayList<Seat> = ArrayList()
)

@Parcelize
data class Seat(
    var seatRow: String = "",
    var seatID: Int = 0,
    var statusAvailable: Boolean = true,
    var statusSelected: Boolean = false,
    var priceList: Int = 0
):Parcelable