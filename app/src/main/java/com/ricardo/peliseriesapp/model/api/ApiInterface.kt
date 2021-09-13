package com.ricardo.peliseriesapp.model.api

import com.ricardo.peliseriesapp.BuildConfig
import com.ricardo.peliseriesapp.model.*
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

    @GET("tv/popular?api_key=cac0e19a99992e43098b093252d62868&language=es-MX")
    fun getPopularSeries() : Call<SerieResponse>

    @GET("tv/{id}?api_key=cac0e19a99992e43098b093252d62868&language=es-MX")
    fun getDetalleSerie(@Path("id") id: Int) : Call<Serie>

    @GET("tv/{id}}/videos?api_key=cac0e19a99992e43098b093252d62868&language=es-MX%2Cen-US")
    fun getSerieVideos(@Path("id") id: Int) : Call<VideoResponse>

    companion object {

        var BASE_URL = "https://api.themoviedb.org/3/"

        fun create() : ApiInterface {

            val levelType: HttpLoggingInterceptor.Level = if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
                HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

            val logging = HttpLoggingInterceptor()
            logging.setLevel(levelType)

            val okhttpClient = OkHttpClient.Builder()
            okhttpClient.addInterceptor(logging)

            val retrofit = Retrofit.Builder().client(okhttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}