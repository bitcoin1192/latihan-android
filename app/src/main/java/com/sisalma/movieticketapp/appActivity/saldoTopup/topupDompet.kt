package com.sisalma.movieticketapp.appActivity.saldoTopup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.appActivity.Home
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.databinding.ActivitySaldoTopupBinding

class topupDompet : AppCompatActivity() {
    lateinit var uiBind: ActivitySaldoTopupBinding
    var topupSaldo = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiBind = ActivitySaldoTopupBinding.inflate(layoutInflater)
        setContentView(uiBind.root)

        val authUser = authenticatedUsers(applicationContext)
        authUser.userAuthenticate()

        val status = arrayListOf(false, false, false, false, false, false)
        val buttonView = arrayListOf(
            uiBind.tv10k,
            uiBind.tv20k,
            uiBind.tv30k,
            uiBind.tv40k,
            uiBind.tv50k,
            uiBind.tv60k
        )
        buttonView.forEachIndexed { index, button ->
            button.setOnClickListener {
                if(status[index]) {
                    topupSaldo -= 10000 * (index + 1)
                    status[index] = false
                    button.setBackgroundResource(R.drawable.shape_line_white)
                } else {
                    topupSaldo += 10000 * (index + 1)
                    status[index] = true
                    button.setBackgroundResource(R.drawable.shape_rectangle_pink)
                }
                updateTextfromButton()
            }
        }

        uiBind.btnTopUp.setOnClickListener {
            authUser.topupSaldo(topupSaldo)
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finishAffinity()
        }

        uiBind.etAmount.setOnClickListener {
            topupSaldo = uiBind.etAmount.text.toString().toInt()
            updateTextfromButton()
        }
    }

    private fun updateTextfromButton() {
        uiBind.etAmount.setText(topupSaldo.toString())
        if(topupSaldo == 0) {
            uiBind.btnTopUp.visibility = View.INVISIBLE
        } else {
            uiBind.btnTopUp.visibility = View.VISIBLE
        }
    }
}