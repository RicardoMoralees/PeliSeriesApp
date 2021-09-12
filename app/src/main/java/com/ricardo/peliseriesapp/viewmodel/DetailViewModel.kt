package com.ricardo.peliseriesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ricardo.peliseriesapp.model.Pelicula
import com.ricardo.peliseriesapp.model.PeliculaProvider

class DetailViewModel : ViewModel() {
    private var pelicula = MutableLiveData<Pelicula>()

    fun getDetallePelicula(id: Int): LiveData<Pelicula> {
        pelicula = PeliculaProvider.getDetallePelicula(id)
        return pelicula
    }
}