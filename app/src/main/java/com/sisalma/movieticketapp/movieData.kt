package com.sisalma.movieticketapp

data class movieDataApi(
    val title: String,
    val genre: String,
    val sinopsis: String,
    val photoLink: String
) {}

data class movieCardData(
    val title: String,
    val genre: String
)

data class movieDetailData(
    val title: String,
    val sinopsis: String,
    val ytEmbed: String
)