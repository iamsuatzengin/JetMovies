package com.suatzengin.whatshouldiwatch.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.suatzengin.whatshouldiwatch.data.paging.SearchPagingSource
import com.suatzengin.whatshouldiwatch.data.remote.movie.Movie
import com.suatzengin.whatshouldiwatch.domain.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    private val _searchedMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val searchedMovies get() = _searchedMovies

    val searchQuery = mutableStateOf("")

    fun searchMovie(query: String) {
        viewModelScope.launch {

            Pager(config = PagingConfig(pageSize = 20), pagingSourceFactory = {
                SearchPagingSource(networkRepository = networkRepository, query = query)
            }).flow.cachedIn(viewModelScope).collect { pagingData ->
                _searchedMovies.value = pagingData
            }


        }
    }
}