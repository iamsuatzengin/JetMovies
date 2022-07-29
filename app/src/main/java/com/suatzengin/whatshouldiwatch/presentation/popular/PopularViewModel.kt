package com.suatzengin.whatshouldiwatch.presentation.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.suatzengin.whatshouldiwatch.data.paging.MoviePagingSource
import com.suatzengin.whatshouldiwatch.domain.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {
    val popularMovies = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        MoviePagingSource(
            networkRepository = networkRepository,
            source = MoviePagingSource.Source.Popular
        )
    }.flow.cachedIn(viewModelScope)

}