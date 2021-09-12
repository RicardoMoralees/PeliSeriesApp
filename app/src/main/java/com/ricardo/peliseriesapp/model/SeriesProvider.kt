package com.ricardo.peliseriesapp.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ricardo.peliseriesapp.model.api.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SeriesProvider {

    private val series = MutableLiveData<List<Serie>>()

        fun getNowPlayingSeries(): MutableLiveData<List<Serie>> {
            val apiInterface = ApiInterface.create().getPlayingNowSeries()

            apiInterface.enqueue(object : Callback<SerieResponse> {
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
}