package com.ricardo.peliseriesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ricardo.peliseriesapp.model.Pelicula
import com.ricardo.peliseriesapp.model.PeliculaProvider
import com.ricardo.peliseriesapp.model.Serie
import com.ricardo.peliseriesapp.model.SeriesProvider

class DetailViewModel : ViewModel() {
    private var pelicula = MutableLiveData<Pelicula>()
    private var serie = MutableLiveData<Serie>()

    fun getDetallePelicula(id: Int): LiveData<Pelicula> {
        pelicula = PeliculaProvider.getDetallePelicula(id)
        return pelicula
    }

    fun getDetalleSerie(id: Int): LiveData<Serie> {
        serie = SeriesProvider.getDetalleSerie(id)
        return serie
    }
}