package com.sisalma.movieticketapp

import android.graphics.Bitmap
import com.google.firebase.database.FirebaseDatabase

abstract class Users {
    lateinit var username: String
    lateinit var password: String
    lateinit var profilePhotoCache: Bitmap
    abstract fun getPhotoLink(): String?
    abstract fun userAuthenticate(): String?
}

abstract class readOnly: Users() {
    abstract fun daftarBaru()
}
abstract class readWrite: Users(){
    abstract fun userDeauthenticate(): String?
    abstract fun setUserData()
    abstract fun setProfilePic()
    abstract fun getSaldo():Int?
    abstract fun buyTicket()
    abstract fun getTicketHistory():ArrayList<>
}
class authenticatedUsers():readWrite(){
    var nama: String = ""
    var saldo = 0
    var url: String = "-"

    var photoProfileLink = ""

    override fun userDeauthenticate(): String? {
        TODO("Local Deauth by ending authenticated user lifecycle")
    }

    override fun getSaldo():Int?{
        return saldo
    }

    override fun setProfilePic() {
        TODO("Not yet implemented")
    }

    override fun setUserData() {
        TODO("Not yet implemented")
    }

    override fun buyTicket() {
        TODO("Not yet implemented")
    }

    override fun getTicketHistory(): ArrayList<Movie> {
        TODO("Not yet implemented")
    }

    override fun getPhotoLink(): String? {
        TODO("Not yet implemented")
    }
}

class guestUser():readOnly(){
    override fun getPhotoLink(): String? {
        TODO("Get default image from firebase storage and set it to profilePhotoCache")
    }

    override fun userAuthenticate(): String? {
        TODO("Let guest be authenticated, check user and password")
    }

    override fun daftarBaru() {
        TODO("Let guest be a member")
    }
}