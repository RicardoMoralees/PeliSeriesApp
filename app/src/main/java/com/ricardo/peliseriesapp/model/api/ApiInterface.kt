package com.ricardo.peliseriesapp.model.api

import com.ricardo.peliseriesapp.model.Pelicula
import com.ricardo.peliseriesapp.model.PeliculaResponse
import com.ricardo.peliseriesapp.model.SerieResponse
import com.ricardo.peliseriesapp.model.VideoResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    //Peliculas
    @GET("movie/now_playing?api_key=cac0e19a99992e43098b093252d62868&language=es-MX")
    fun getPlayingNowMovies() : Call<PeliculaResponse>

    @GET("movie/popular?api_key=cac0e19a99992e43098b093252d62868&language=es-MX")
    fun getPopularMovies() : Call<PeliculaResponse>

    @GET("movie/{id}?api_key=cac0e19a99992e43098b093252d62868&language=es_MX")
    fun getDetallePelicula(@Path("id") id: Int) : Call<Pelicula>

    @GET("movie/{id}/videos?api_key=cac0e19a99992e43098b093252d62868&language=es-MX%2C%20en-US")
    fun getPeliculasVideos(@Path("id") id: Int) : Call<VideoResponse>

    //Series
    @GET("tv/on_the_air?api_key=cac0e19a99992e43098b093252d62868&language=es-MX")
    fun getPlayingNowSeries() : Call<SerieResponse>

    companion object {

        var BASE_URL = "https://api.themoviedb.org/3/"

        fun create() : ApiInterface {

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder().client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}