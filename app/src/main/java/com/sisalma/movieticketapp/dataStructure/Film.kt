package com.sisalma.movieticketapp.dataStructure

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Film (
    var desc: String = "",
    var director: String = "",
    var genre: String = "",
    var judul: String = "",
    var play: HashMap<String,HashMap<String,String>> = HashMap(),
    var poster: String = "",
    var rating: String = ""
):Parcelable