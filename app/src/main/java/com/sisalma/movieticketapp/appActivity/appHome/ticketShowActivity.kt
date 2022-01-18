package com.sisalma.movieticketapp.appActivity.appHome

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
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
        val ticket = intent.getParcelableExtra<ticketData>("ticketData")

        data?.let {
            uiBind.tvTitle.text= it.judul
            uiBind.tvGenre.text = it.genre
            uiBind.tvRate.text = it.rating
            Glide.with(this)
                .load(it.poster)
                .into(uiBind.ivPosterImage)

            uiBind.rcCheckout.layoutManager = LinearLayoutManager(this)
            ticket?.let {
                Log.i("tshowAct", it.toString())
                uiBind.rcCheckout.adapter = SeatAdapterTheme(it.selectedSeat,ContextCompat.getColor(this@ticketShowActivity,R.color.Global_blue))
                val bitmap = generateQRBitmap(it.qrData)
                uiBind.ivBarcode.setOnClickListener {
                    showDialogQR(bitmap)
                }
            }
        }

        uiBind.ivClose.setOnClickListener {
            finish()
        }

    }

    private fun showDialogQR(input: Bitmap) {
        val dialog = Dialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_qr)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true)

        val imageViewHolder = dialog.findViewById(R.id.iv_qr) as ImageView
        imageViewHolder.setImageBitmap(input)

        val tvDesc = dialog.findViewById(R.id.tv_desc) as TextView
        tvDesc.text = "Silakan scan barcode dengan mesin pencetak terdekat"

        val btnClose = dialog.findViewById(R.id.btn_close) as Button
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun generateQRBitmap(input: String): Bitmap {
        val width = 128
        val height = 128
        val bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()
        val bitMatrix = codeWriter.encode(input, BarcodeFormat.QR_CODE, width, height)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }
}