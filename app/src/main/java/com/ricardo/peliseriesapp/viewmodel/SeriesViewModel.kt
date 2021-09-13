package com.ricardo.peliseriesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ricardo.peliseriesapp.model.Pelicula
import com.ricardo.peliseriesapp.model.PeliculaProvider
import com.ricardo.peliseriesapp.model.Serie
import com.ricardo.peliseriesapp.model.SeriesProvider

class SeriesViewModel : ViewModel() {

    private var series = MutableLiveData<List<Serie>>()

    fun getNowPLayingSeries(): LiveData<List<Serie>> {
        series = SeriesProvider.getNowPlayingSeries()
        return series
    }

    fun getSeriesPopulares(): LiveData<List<Serie>> {
        series = SeriesProvider.getSeriesPopulares()
        return series
    }
}