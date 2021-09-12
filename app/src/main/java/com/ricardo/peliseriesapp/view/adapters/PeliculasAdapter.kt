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
import com.ricardo.peliseriesapp.model.Pelicula

class PeliculasAdapter(var peliculas: List<Pelicula>, val context: Context, val listener: PeliculasClickListener):
    RecyclerView.Adapter<PeliculasAdapter.PeliculasViewHolder>() {


    class PeliculasViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.tv_titulo_pelicula)
        val ivPortada: AppCompatImageView = view.findViewById(R.id.iv_portada_pelicula)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculasViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pelicula, parent, false)

        return PeliculasViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeliculasViewHolder, position: Int) {
        val pelicula: Pelicula = peliculas.get(position)

        holder.tvTitulo.text = pelicula.title
        val url: String = "https://image.tmdb.org/t/p/original" + pelicula.poster_path
        Glide.with(context).load(url)
            .placeholder(R.drawable.ic_baseline_local_movies_24)
            .error(R.drawable.ic_image_not_found_24)
            .into(holder.ivPortada)

        holder.itemView.setOnClickListener({
            listener.onPeliculaCLicked(pelicula)
        })
    }

    override fun getItemCount(): Int = peliculas.size

    interface PeliculasClickListener {
        fun onPeliculaCLicked(pelicula: Pelicula)
    }
}