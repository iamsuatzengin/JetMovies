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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _state = mutableStateOf(WatchListState())
    val state: State<WatchListState> get() = _state

    init {
        getWatchList()
    }

    fun onEvent(event: WatchListEvent){
        when(event){
            is WatchListEvent.SwipeToDelete -> {
                viewModelScope.launch(Dispatchers.IO) {
                    localRepository.delete(movie = event.movie)
                }
            }
        }
    }

    private fun getWatchList() {
        viewModelScope.launch {
            localRepository.getWatchList().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result) {
                        is Resource.Success -> {
                            _state.value = WatchListState(
                                list = result.data ?: emptyList(),
                                isEmpty = result.data.isNullOrEmpty()
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = WatchListState(isLoading = true, isEmpty = false)
                        }
                        is Resource.Error -> {
                            _state.value =
                                WatchListState(isEmpty = false, error = "${result.message} Error")
                        }
                    }

                }
            }
        }
    }
}

sealed class WatchListEvent{
    data class SwipeToDelete(val movie: MovieEntity): WatchListEvent()
}