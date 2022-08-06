package com.suatzengin.whatshouldiwatch.presentation.watch_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.whatshouldiwatch.data.local.entities.MovieEntity
import com.suatzengin.whatshouldiwatch.domain.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
            is WatchListEvent.DeleteMovie -> {
                viewModelScope.launch(Dispatchers.IO) {
                    localRepository.delete(movie = event.movie)
                    deletedMovie = event.movie
                }
            }
            is WatchListEvent.RestoreMovie -> {
                viewModelScope.launch(Dispatchers.IO) {
                    localRepository.insert(deletedMovie ?: return@launch)
                    deletedMovie = null
                }
            }
        }
    }

    private fun getWatchList() {
        _state.value = _state.value.copy(
            isLoading = true
        )
        localRepository.getWatchList().onEach {
                _state.value = _state.value.copy(
                    list = it.toMutableList(),
                    isLoading = false,
                    isEmpty = it.isEmpty(),
                )
            }
            .catch { e ->
                _state.value = _state.value.copy(
                    list = arrayListOf(),
                    isLoading = false,
                    isEmpty = false,
                    error = e.message ?: e.localizedMessage
                )
            }
            .launchIn(viewModelScope)
    }
}

sealed class WatchListEvent {
    data class DeleteMovie(val movie: MovieEntity) : WatchListEvent()
    object RestoreMovie : WatchListEvent()
}