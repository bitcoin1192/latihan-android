package com.sisalma.movieticketapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class moviecardAdapter(dataFiller: List<movieCardData>): RecyclerView.Adapter<moviecardAdapter.ViewHolder>() {
    var data = dataFiller
    var length = data.size
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val movieTitle = view.findViewById<TextView>(R.id.movieTitle)
        val movieGenre = view.findViewById<TextView>(R.id.movieGenre)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val provinceList = inflater.inflate(R.layout.homecardfragment, parent,false)

        return ViewHolder(provinceList)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rawData = data
        holder.movieTitle.text = rawData[position].title
        holder.movieGenre.text = rawData[position].genre
    }

    override fun getItemCount(): Int {
        return length
    }
}