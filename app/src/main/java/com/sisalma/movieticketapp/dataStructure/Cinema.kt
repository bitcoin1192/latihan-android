package com.sisalma.movieticketapp.dataStructure

data class cinemaDetail(
    val namaCinema: String = "",
    val Lokasi: String = ""
)

data class sesiTayang(
    val namaFilm: String = "",
    val ruangTayang: Int = 1,
    val tanggalTayang: String = "",
    val priceList: Int = 0
)
data class availableSeat(
    val availableSeat: ArrayList<seat> = ArrayList()
)
data class seat(
    val statusAvailable: Boolean = true
)