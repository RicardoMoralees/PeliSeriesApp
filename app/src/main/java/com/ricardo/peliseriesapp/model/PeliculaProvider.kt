package com.ricardo.peliseriesapp.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ricardo.peliseriesapp.model.api.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PeliculaProvider {

    private val peliculas = MutableLiveData<List<Pelicula>>()
    private val videos = MutableLiveData<List<Video>>()
    private val pelicula = MutableLiveData<Pelicula>()

        fun getNowPlayingPeliculas(): MutableLiveData<List<Pelicula>> {
            val call = ApiInterface.create().getPlayingNowMovies()

            call.enqueue(object : Callback<PeliculaResponse> {
                override fun onResponse(
                    call: Call<PeliculaResponse>?,
                    response: Response<PeliculaResponse>?
                ) {
                    if (response?.body() != null)
                        peliculas.value = response.body()!!.results
                }

                override fun onFailure(call: Call<PeliculaResponse>?, t: Throwable?) {
                    Log.e("CallError", t?.message.toString())
                    peliculas.value = listOf()
                }
            })

            return peliculas
        }

    fun getDetallePelicula(id: Int): MutableLiveData<Pelicula> {
        val call = ApiInterface.create().getDetallePelicula(id)

        call.enqueue(object : Callback<Pelicula> {
            override fun onResponse(
                call: Call<Pelicula>?,
                response: Response<Pelicula>?
            ) {
                if (response?.body() != null)
                    pelicula.value = response.body()!!
            }

            override fun onFailure(call: Call<Pelicula>?, t: Throwable?) {
                Log.e("CallError", t?.message.toString())
                peliculas.value = listOf()
            }
        })

        return pelicula
    }

    fun getVideoPeliculas(videoId: Int): MutableLiveData<List<Video>> {
        val call = ApiInterface.create().getPeliculasVideos(videoId)

        call.enqueue(object : Callback<VideoResponse> {
            override fun onResponse(
                call: Call<VideoResponse>?,
                response: Response<VideoResponse>?
            ) {
                if (response?.body() != null)
                    videos.value = response.body()!!.results
            }

            override fun onFailure(call: Call<VideoResponse>?, t: Throwable?) {
                Log.e("CallError", t?.message.toString())
                peliculas.value = listOf()
            }
        })

        return videos
    }
}