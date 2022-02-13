package com.sisalma.movieticketapp.appActivity.saldoTopup

import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisalma.movieticketapp.dataStructure.TicketData
import com.sisalma.movieticketapp.dataUser
import com.sisalma.movieticketapp.databinding.ActivitySaldoHistoryBinding
import java.util.*

class historiDompet : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var uiBind = ActivitySaldoHistoryBinding.inflate(layoutInflater)
        setContentView(uiBind.root)

        val saldoUser = intent.getParcelableExtra<dataUser>("saldoUser")
        val ticketHistory = intent.getParcelableArrayListExtra<TicketData>("ticketData")
        val number = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        uiBind.ivButtonBack.setOnClickListener {
            finish()
        }

        uiBind.btnTopUp.setOnClickListener {
            val intent = Intent(this, topupDompet::class.java)
            startActivity(intent)
        }

        saldoUser?.saldo.let {
            uiBind.currentSaldo.text = number.format(it)
        }

        ticketHistory?.let {
            uiBind.rvTransaksi.layoutManager = LinearLayoutManager(this)
            uiBind.rvTransaksi.adapter = rowTransactionAdapter(it)
        }

    }

}