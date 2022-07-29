package com.suatzengin.whatshouldiwatch.domain.repository

import com.suatzengin.whatshouldiwatch.common.Resource
import com.suatzengin.whatshouldiwatch.data.remote.genre.GenreResponse
import com.suatzengin.whatshouldiwatch.data.remote.movie.MovieResponse
import com.suatzengin.whatshouldiwatch.domain.model.HomeType
import com.suatzengin.whatshouldiwatch.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow


interface NetworkRepository {

    suspend fun getTopRatedMovies(page: Int): MovieResponse

    suspend fun getGenres(): GenreResponse

    suspend fun getPopularMovies(page: Int): MovieResponse

    fun getMovieById(id: Int): Flow<Resource<MovieDetail>>

    suspend fun getMovieWithGenres(page: Int, genreId: Int): MovieResponse

    suspend fun searchMovie(page: Int, query: String): MovieResponse

    fun getHomeMovies(): Flow<Resource<List<HomeType>>>
}