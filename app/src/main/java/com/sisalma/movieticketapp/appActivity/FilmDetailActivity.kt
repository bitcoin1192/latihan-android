package com.sisalma.movieticketapp.appActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sisalma.movieticketapp.appActivity.buyTicket.buyTicketActivity
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.databinding.ActivityDetailBinding

class filmDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailParcel = intent.getParcelableExtra<Film>("filmDetail")!!
        binding.tvKursi.text = detailParcel.judul
        binding.tvGenre.text = detailParcel.genre
        binding.tvDesc.text = detailParcel.desc
        binding.tvRate.text = detailParcel.rating
        Glide.with(this)
            .load(detailParcel.poster)
            .into(binding.ivPoster)
        Log.i("Hashmap Test",detailParcel.play.toString())

        val player = playerHashMap2Array(detailParcel)

        binding.rvWhoPlay.adapter = PlaysAdapter(player)
        binding.btnPilihBangku.setOnClickListener {
            val intent = Intent(this,buyTicketActivity::class.java)
                .putExtra("filmData",detailParcel)
            startActivity(intent)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.rvWhoPlay.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun playerHashMap2Array(filmData: Film): ArrayList<Playlist>{
        val playerHashMap = filmData.play
        var playerList = ArrayList<Playlist>()
        playerHashMap.forEach{
            playerList.add(Playlist(it.component2()["nama"],it.component2()["url"]))
        }
        Log.i("playListArr",playerList.toString())
        return playerList
    }
}
