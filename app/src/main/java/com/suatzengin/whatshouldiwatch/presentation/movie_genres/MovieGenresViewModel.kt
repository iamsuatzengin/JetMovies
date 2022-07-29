package com.suatzengin.whatshouldiwatch.presentation.movie_genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.suatzengin.whatshouldiwatch.data.paging.MoviePagingSource
import com.suatzengin.whatshouldiwatch.data.remote.movie.Movie
import com.suatzengin.whatshouldiwatch.domain.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieGenresViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    fun moviesWithGenres(genreId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MoviePagingSource(
                    networkRepository,
                    MoviePagingSource.Source.MovieWithGenres(genreId = genreId)
                )
            }
        ).flow.cachedIn(viewModelScope)
    }
}