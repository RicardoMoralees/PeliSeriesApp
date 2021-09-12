package com.ricardo.peliseriesapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ricardo.peliseriesapp.R
import com.ricardo.peliseriesapp.model.Serie

class SeriesAdapter(var series: List<Serie>, val context: Context, val listener: SeriesClickListener):
    RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {


    class SeriesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.tv_titulo_pelicula)
        val ivPortada: AppCompatImageView = view.findViewById(R.id.iv_portada_pelicula)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pelicula, parent, false)

        return SeriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val serie: Serie = series.get(position)

        holder.tvTitulo.text = serie.name
        val url: String = "https://image.tmdb.org/t/p/original" + serie.poster_path
        Glide.with(context).load(url)
            .placeholder(R.drawable.ic_baseline_local_movies_24)
            .error(R.drawable.ic_image_not_found_24)
            .into(holder.ivPortada)

        holder.itemView.setOnClickListener({
            listener.onSerieCLicked(serie)
        })
    }

    override fun getItemCount(): Int = series.size

    interface SeriesClickListener {
        fun onSerieCLicked(serie: Serie)
    }
}