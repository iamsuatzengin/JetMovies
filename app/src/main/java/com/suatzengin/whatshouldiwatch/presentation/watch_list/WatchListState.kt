package com.suatzengin.whatshouldiwatch.presentation.watch_list

import com.suatzengin.whatshouldiwatch.data.local.entities.MovieEntity

data class WatchListState(
    val list: MutableList<MovieEntity> = mutableListOf(),
    val isEmpty: Boolean = true,
    val isLoading: Boolean = false,
    val error: String = ""
)
