package com.suatzengin.whatshouldiwatch.presentation.detail

import com.suatzengin.whatshouldiwatch.data.local.entities.MovieEntity


sealed class MoviesEvent {
    data class BookmarkMovie(val movie: MovieEntity) : MoviesEvent()
    data class DeleteMovie(val movie: MovieEntity): MoviesEvent()
    data class IsBookmarked(val id: Int): MoviesEvent()
}