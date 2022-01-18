package com.sisalma.movieticketapp.appActivity.appHome.homeFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sisalma.movieticketapp.R
import com.sisalma.movieticketapp.dataStructure.Film
import com.sisalma.movieticketapp.dataStructure.ticketData

class ComingSoonAdapter(private var data: ArrayList<Film>, private val listener: (Film) -> Unit) :
    RecyclerView.Adapter<LeagueViewHolder>() {

    lateinit var ContextAdapter: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View =
            layoutInflater.inflate(R.layout.row_item_coming_soon, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = data.size
}

class ticketRowAdapter(
    var filmList: HashMap<String, Film>,
    var ticketList: ArrayList<ticketData>,
    val listener: (Film, ticketData) -> Unit
) : RecyclerView.Adapter<ticketRowAdapter.ViewHolder>() {
    lateinit var ContextAdapter: Context

    inner class ViewHolder(view: View) : LeagueViewHolder(view) {
        fun bindTicketItem(
            data: Film,
            ticket: ticketData,
            listener: (Film, ticketData) -> Unit,
            context: Context
        ) {
            tvTitle.text = data.judul
            tvGenre.text = data.genre
            tvRate.text = data.rating

            Glide
                .with(context)
                .load(data.poster)
                .into(tvImage)

            itemView.setOnClickListener {
                listener(data, ticket)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View =
            layoutInflater.inflate(R.layout.row_item_coming_soon, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        filmList[ticketList[position].namaFilm]?.let {
            holder.bindTicketItem(it, ticketList[position], listener, ContextAdapter)
        }
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }
}

open class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    protected val tvTitle: TextView = view.findViewById(R.id.tv_kursi)
    protected val tvGenre: TextView = view.findViewById(R.id.tv_genre)
    protected val tvRate: TextView = view.findViewById(R.id.tv_rate)

    protected val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)

    fun bindItem(data: Film, listener: (Film) -> Unit, context: Context, position: Int) {

        tvTitle.text = data.judul
        tvGenre.text = data.genre
        tvRate.text = data.rating

        Glide
            .with(context)
            .load(data.poster)
            .into(tvImage)

        itemView.setOnClickListener {
            listener(data)
        }
    }

}
