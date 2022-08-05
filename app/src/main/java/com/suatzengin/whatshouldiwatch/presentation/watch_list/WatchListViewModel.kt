package com.suatzengin.whatshouldiwatch.presentation.watch_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.whatshouldiwatch.common.Resource
import com.suatzengin.whatshouldiwatch.data.local.entities.MovieEntity
import com.suatzengin.whatshouldiwatch.domain.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _state = mutableStateOf(WatchListState())
    val state: State<WatchListState> get() = _state

    private var deletedMovie: MovieEntity? = null

    init {
        getWatchList()
    }

    fun onEvent(event: WatchListEvent) {
        when (event) {
            is WatchListEvent.WatchList -> {
                getWatchList()
            }
            is WatchListEvent.DeleteMovie -> {
                viewModelScope.launch(Dispatchers.IO) {
                    localRepository.delete(movie = event.movie)
                    deletedMovie = event.movie
                    _state.value = state.value.copy(
                        list = state.value.list.filter {
                            it != deletedMovie
                        } as MutableList<MovieEntity>
                    )
                }
            }
            is WatchListEvent.RestoreMovie -> {
                viewModelScope.launch(Dispatchers.IO) {
                    localRepository.insert(deletedMovie ?: return@launch)
                    deletedMovie = null
                    getWatchList()
                }
            }
        }
    }

    private fun getWatchList() {
        viewModelScope.launch {
            localRepository.getWatchList().distinctUntilChanged().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                list = (result.data ?: mutableListOf()) as MutableList<MovieEntity>,
                                isEmpty = result.data.isNullOrEmpty(),
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                isLoading = true,
                                isEmpty = false,
                                list = arrayListOf(), error = ""
                            )
                        }
                        is Resource.Error -> {
                            _state.value =
                                state.value.copy(isEmpty = false, error = "${result.message} Error")
                        }
                    }
                }
            }
        }
    }
}

sealed class WatchListEvent {
    object WatchList : WatchListEvent()
    data class DeleteMovie(val movie: MovieEntity) : WatchListEvent()
    object RestoreMovie : WatchListEvent()
}