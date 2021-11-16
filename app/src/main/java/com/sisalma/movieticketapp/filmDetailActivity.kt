package com.bagicode.bwamov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.bwamov.home.dashboard.PlaysAdapter
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.sisalma.movieticketapp.appActivity.Film
import com.sisalma.movieticketapp.appActivity.Playlist
import com.sisalma.movieticketapp.databinding.ActivityDetailBinding
import com.sisalma.movieticketapp.viewModelFilm

class filmDetailActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Playlist>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityDetailBinding.inflate(layoutInflater)
        val viewMode = viewModelFilm()
        val test = viewMode.getFilmData()
        setContentView(binding.root)

        val stringParcel = intent.getParcelableArrayExtra("filmIndex")

        test.observe(this,{
            //Some adapter to handle list film
            binding.tvKursi.text = it[0].judul  ?:"Text"
            binding.tvGenre.text = it[0].genre  ?:"Genre not found"
            binding.tvDesc.text = it[0].desc    ?:"TV Description not found"
            binding.tvRate.text = it[0].rating  ?:"TV Rating not found"
            Glide.with(this@filmDetailActivity)
                .load(it[0].poster)
                .into(binding.ivPoster)
        })

        binding.btnPilihBangku.setOnClickListener {

        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.rvWhoPlay.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()
    }

    private fun getData() {
        //TODO("Move method to viewModelFilm to fetch data, additionally use liveData to update UI")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (getdataSnapshot in dataSnapshot.getChildren()) {
                    val film = getdataSnapshot.getValue(Film::class.java)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@filmDetailActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
