package com.sisalma.movieticketapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.bwamov.home.dashboard.PlaysAdapter
import com.bumptech.glide.Glide
import com.sisalma.movieticketapp.appActivity.Film
import com.sisalma.movieticketapp.appActivity.Playlist
import com.sisalma.movieticketapp.databinding.ActivityDetailBinding

class filmDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailParcel: Film = intent.getParcelableExtra<Film>("filmDetail")?:Film()
        binding.tvKursi.text = detailParcel.judul
        binding.tvGenre.text = detailParcel.genre
        binding.tvDesc.text = detailParcel.desc
        binding.tvRate.text = detailParcel.rating
        Glide.with(this)
            .load(detailParcel.poster)
            .into(binding.ivPoster)
        Log.i("Hashmap Test",detailParcel.play.toString())

        //binding.rvWhoPlay.adapter = PlaysAdapter(detailParcel.play){

        //}
        binding.btnPilihBangku.setOnClickListener {

        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.rvWhoPlay.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }
}
