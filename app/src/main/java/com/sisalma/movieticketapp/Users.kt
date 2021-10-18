package com.sisalma.movieticketapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Users():Parcelable{
    var email: String ?= ""
    var nama: String ?= ""
    var password: String ?= ""
    var saldo: String ?= "0"
    var url: String ?= "-"
    var username: String ?= ""
}