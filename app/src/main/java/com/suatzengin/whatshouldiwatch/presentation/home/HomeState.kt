package com.suatzengin.whatshouldiwatch.presentation.home

import com.suatzengin.whatshouldiwatch.domain.model.HomeType

data class HomeState(
    val homeList : List<HomeType> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)