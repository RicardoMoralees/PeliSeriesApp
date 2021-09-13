package com.ricardo.peliseriesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ricardo.peliseriesapp.model.Pelicula
import com.ricardo.peliseriesapp.model.PeliculaProvider

class PeliculaViewModel : ViewModel() {

    private var peliculas = MutableLiveData<List<Pelicula>>()

    fun getNowPLayingPeliculas(): LiveData<List<Pelicula>> {
        peliculas = PeliculaProvider.getNowPlayingPeliculas()
        return peliculas
    }

    fun getPeliculasPopulares(): LiveData<List<Pelicula>> {
        peliculas = PeliculaProvider.getPeliculasPopulares()
        return peliculas
    }
}