package com.sisalma.movieticketapp.appActivity.appHome

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.appActivity.buyTicket.paymentConfirmFragment.SeatAdapterTheme
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.dataStructure.ticketData
import com.sisalma.movieticketapp.databinding.ActivityTicketShowBinding
import kotlinx.coroutines.*

class ticketShowActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var uiBind = ActivityTicketShowBinding.inflate(layoutInflater)
        setContentView(uiBind.root)

        val data = intent.getParcelableExtra<Film>("filmDetail")
        val seat = intent.getParcelableExtra<ticketData>("ticketData")
        data?.let {
            uiBind.tvTitle.text= it.judul
            uiBind.tvGenre.text = it.genre
            uiBind.tvRate.text = it.rating
            Glide.with(this)
                .load(it.poster)
                .into(uiBind.ivPosterImage)

            uiBind.rcCheckout.layoutManager = LinearLayoutManager(this)
            seat?.let {
                Log.i("tshowAct", it.toString())
                uiBind.rcCheckout.adapter = SeatAdapterTheme(it.selectedSeat,ContextCompat.getColor(this@ticketShowActivity,R.color.Global_blue))
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