package com.ricardo.peliseriesapp.model

data class Serie (
    val id:Int,
    val name:String,
    val popularity:Float,
    val vote_average:Float,
    val poster_path:String,
    val first_air_date: String,
    val overview: String,
    val genres: List<Genre>,
    val homepage: String,
    val number_of_episodes: Int,
    val number_of_seasons: Int
)