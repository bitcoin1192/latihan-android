package com.sisalma.movieticketapp.appActivity.saldoTopup

import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.dataUser
import com.sisalma.movieticketapp.databinding.ActivitySaldoHistoryBinding
import java.util.*

class historiDompet: AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var uiBind = ActivitySaldoHistoryBinding.inflate(layoutInflater)
        setContentView(uiBind.root)

        val saldoUser = intent.getParcelableExtra<dataUser>("saldoUser")!!
        val authUser = authenticatedUsers(applicationContext)
        authUser.userAuthenticate()
        val number = NumberFormat.getCurrencyInstance(Locale("id","ID"))
        uiBind.ivButtonBack.setOnClickListener{
            finish()
        }

        uiBind.btnTopUp.setOnClickListener {
            val intent = Intent(this,topupDompet::class.java)
            startActivity(intent)
        }

        uiBind.currentSaldo.text = number.format(saldoUser.saldo)

    }

}