package com.suatzengin.whatshouldiwatch.domain.model

import com.suatzengin.whatshouldiwatch.data.remote.genre.Genre
import com.suatzengin.whatshouldiwatch.data.remote.movie.MovieResponse
import com.suatzengin.whatshouldiwatch.data.remote.movie_detail.Credit
import com.suatzengin.whatshouldiwatch.data.remote.movie_detail.Language

data class MovieDetail(
    val id: Int,
    val backdropPath: String?,
    val genres: List<Genre>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String,
    val runtime: Int?,
    val spokenLanguages: List<Language>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val similar: MovieResponse,
    val credit: Credit
)
