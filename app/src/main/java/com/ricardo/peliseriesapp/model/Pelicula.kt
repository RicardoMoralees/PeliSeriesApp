package com.ricardo.peliseriesapp.model

data class Pelicula (
    val title:String,
    val id:Int,
    val popularity:Float,
    val release_date:String,
    val vote_average:Float,
    val poster_path:String,
    val overview: String,
    val genres: List<Genre>,
    val homepage: String
    )