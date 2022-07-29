package com.suatzengin.whatshouldiwatch.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.whatshouldiwatch.common.Resource
import com.suatzengin.whatshouldiwatch.domain.repository.LocalRepository
import com.suatzengin.whatshouldiwatch.domain.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val localRepository: LocalRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(MovieDetailState())
    val state: State<MovieDetailState> get() = _state

    init {
        savedStateHandle.get<String>("movie_id")?.let {
            getMovieById(it.toInt())
        }
    }

    private val _isBookmarked = mutableStateOf(false)
    val isBookmarked: State<Boolean> get() = _isBookmarked


    fun onEvent(event: MoviesEvent) {
        when (event) {
            is MoviesEvent.BookmarkMovie -> {
                viewModelScope.launch(Dispatchers.IO) {
                    localRepository.insert(event.movie)
                    withContext(Dispatchers.Main){
                        _isBookmarked.value = true
                    }
                }
            }
            is MoviesEvent.DeleteMovie -> {
                viewModelScope.launch(Dispatchers.IO) {
                    localRepository.delete(event.movie)
                    withContext(Dispatchers.Main){
                        _isBookmarked.value = false
                    }
                }
            }
            is MoviesEvent.IsBookmarked ->{
                viewModelScope.launch(Dispatchers.IO) {
                    val movie = localRepository.getMovieById(id = event.id)
                    withContext(Dispatchers.Main) {
                        _isBookmarked.value = movie != null
                    }
                }
            }
        }
    }

    private fun getMovieById(id: Int) {
        viewModelScope.launch {
            networkRepository.getMovieById(id = id)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = MovieDetailState(movie = result.data)
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true)
                        }
                        is Resource.Error -> {
                            _state.value = _state.value.copy(error = result.message ?: "Error!")
                        }
                    }
                }
        }
    }
}
