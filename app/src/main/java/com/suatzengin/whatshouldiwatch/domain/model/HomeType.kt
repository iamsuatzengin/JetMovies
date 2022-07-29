package com.suatzengin.whatshouldiwatch.domain.model

import com.suatzengin.whatshouldiwatch.data.remote.movie.Movie


sealed class HomeType {
    data class Popular(val popular: List<Movie>) : HomeType()
    data class TopRated(val topRated: List<Movie>) : HomeType()
}