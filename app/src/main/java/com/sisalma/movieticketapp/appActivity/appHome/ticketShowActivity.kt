package com.sisalma.movieticketapp.appActivity.appHome

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.appActivity.appHome.ticketFragment.viewTicketModel
import com.sisalma.movieticketapp.appActivity.buyTicket.paymentConfirmFragment.SeatAdapterTheme
import com.sisalma.movieticketapp.appActivity.buyTicket.paymentConfirmFragment.seatAdapter
import com.sisalma.movieticketapp.appActivity.saldoTopup.rowTransactionAdapter
import com.sisalma.movieticketapp.authenticatedUsers
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.databinding.ActivityTicketShowBinding
import com.sisalma.movieticketapp.repository.userRepository
import kotlinx.coroutines.*

class ticketShowActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var uiBind = ActivityTicketShowBinding.inflate(layoutInflater)
        setContentView(uiBind.root)

        val username = applicationContext.getSharedPreferences("app-setting", MODE_PRIVATE)
            .getString("username","")!!
        val password = applicationContext.getSharedPreferences("app-setting", MODE_PRIVATE)
            .getString("password","")!!
        val data = intent.getParcelableExtra<Film>("filmDetail")!!

        uiBind.tvTitle.text= data.judul
        uiBind.tvGenre.text = data.genre
        uiBind.tvRate.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .into(uiBind.ivPosterImage)

        uiBind.rcCheckout.layoutManager = LinearLayoutManager(this)

        val userObject = authenticatedUsers()
        userObject.userAuthenticate(username,password)

        val userCheckCoroutine = CoroutineScope(Dispatchers.Main)
        userCheckCoroutine.launch {
            var result = async { userObject.testAuthorizeUser(userObject) }
            if (result.await()) {
                val new = userRepository(userObject)
                delay(1500)
                uiBind.rcCheckout.adapter = SeatAdapterTheme(new.getUserSeat(data.judul),ContextCompat.getColor(this@ticketShowActivity,R.color.Global_blue))
            }
        }

        uiBind.ivClose.setOnClickListener {
            finish()
        }

        uiBind.ivBarcode.setOnClickListener {
            showDialog("Silahkan melakukan scanning pada counter tiket terdekat")
        }

    }

    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_qr)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true)
        val tvDesc = dialog.findViewById(R.id.tv_desc) as TextView
        tvDesc.text = title

        val btnClose = dialog.findViewById(R.id.btn_close) as Button
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
}