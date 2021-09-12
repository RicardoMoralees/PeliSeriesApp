package com.ricardo.peliseriesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ricardo.peliseriesapp.model.PeliculaProvider
import com.ricardo.peliseriesapp.model.Video

class VideoViewModel : ViewModel() {
    private var videos = MutableLiveData<List<Video>>()

    fun getVideos(id: Int): LiveData<List<Video>> {
        videos = PeliculaProvider.getVideoPeliculas(id)
        return videos
    }
}