package com.suatzengin.whatshouldiwatch.domain.repository

import com.suatzengin.whatshouldiwatch.common.Resource
import com.suatzengin.whatshouldiwatch.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {


    suspend fun insert(movie: MovieEntity)

    suspend fun delete(movie: MovieEntity)

    fun getWatchList(): Flow<Resource<List<MovieEntity>>>

    suspend fun getMovieById(id: Int): MovieEntity?
}