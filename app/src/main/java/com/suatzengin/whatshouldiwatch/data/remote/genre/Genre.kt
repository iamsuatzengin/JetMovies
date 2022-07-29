package com.suatzengin.whatshouldiwatch.data.remote.genre

data class GenreResponse(
    val genres: List<Genre>
)
data class Genre(
    val id: Int,
    val name: String
)