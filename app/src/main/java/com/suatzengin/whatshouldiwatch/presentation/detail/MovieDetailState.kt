package com.suatzengin.whatshouldiwatch.presentation.detail

import com.suatzengin.whatshouldiwatch.domain.model.MovieDetail

data class MovieDetailState(
    val movie: MovieDetail? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val isBookmarked: Boolean = false
)
