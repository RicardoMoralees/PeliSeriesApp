package com.ricardo.peliseriesapp.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ricardo.peliseriesapp.model.api.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SeriesProvider {

    private val series = MutableLiveData<List<Serie>>()
    private val serie = MutableLiveData<Serie>()
    private val videos = MutableLiveData<List<Video>>()

        fun getNowPlayingSeries(): MutableLiveData<List<Serie>> {
            val call = ApiInterface.create().getPlayingNowSeries()

            call.enqueue(object : Callback<SerieResponse> {
                override fun onResponse(
                    call: Call<SerieResponse>?,
                    response: Response<SerieResponse>?
                ) {
                    if (response?.body() != null)
                        series.value = response.body()!!.results
                }

                override fun onFailure(call: Call<SerieResponse>?, t: Throwable?) {
                    Log.e("CallError", t?.message.toString())
                    series.value = listOf()
                }
            })

            return series
        }

    fun getSeriesPopulares(): MutableLiveData<List<Serie>> {
        val call = ApiInterface.create().getPopularSeries()

        call.enqueue(object : Callback<SerieResponse> {
            override fun onResponse(
                call: Call<SerieResponse>?,
                response: Response<SerieResponse>?
            ) {
                if (response?.body() != null)
                    series.value = response.body()!!.results
            }

            override fun onFailure(call: Call<SerieResponse>?, t: Throwable?) {
                Log.e("CallError", t?.message.toString())
                series.value = listOf()
            }
        })

        return series
    }

    fun getDetalleSerie(serieId: Int): MutableLiveData<Serie> {
        val call = ApiInterface.create().getDetalleSerie(serieId)

        call.enqueue(object : Callback<Serie> {
            override fun onResponse(
                call: Call<Serie>?,
                response: Response<Serie>?
            ) {
                if (response?.body() != null)
                    serie.value = response.body()!!
            }

            override fun onFailure(call: Call<Serie>?, t: Throwable?) {
                Log.e("CallError", t?.message.toString())
                series.value = listOf()
            }
        })

        return serie
    }

    fun getVideoSeries(serieId: Int): MutableLiveData<List<Video>> {
        val call = ApiInterface.create().getSerieVideos(serieId)

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
                videos.value = listOf()
            }
        })

        return videos
    }
}