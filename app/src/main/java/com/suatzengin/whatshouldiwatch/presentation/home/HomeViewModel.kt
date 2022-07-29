package com.suatzengin.whatshouldiwatch.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.whatshouldiwatch.common.Resource
import com.suatzengin.whatshouldiwatch.domain.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val networkRepository: NetworkRepository
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state get() = _state

    init {
        getHomeUc()
    }

    private fun getHomeUc() {
        networkRepository.getHomeMovies().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = HomeState(homeList = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = HomeState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = HomeState(error = result.message ?: "An unexpected error occured.")
                }
            }
        }.launchIn(viewModelScope)
    }

}