package com.suatzengin.whatshouldiwatch.data.repository

import com.suatzengin.whatshouldiwatch.data.local.WatchListDao
import com.suatzengin.whatshouldiwatch.data.local.entities.MovieEntity
import com.suatzengin.whatshouldiwatch.domain.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val dao: WatchListDao
) : LocalRepository {


    override suspend fun insert(movie: MovieEntity) {
        dao.insert(movie = movie)
    }

    override suspend fun delete(movie: MovieEntity) {
        dao.delete(movie = movie)
    }

    override fun getWatchList(): Flow<List<MovieEntity>> = dao.getWatchList().flowOn(Dispatchers.IO)

    override suspend fun getMovieById(id: Int): MovieEntity? {
        return dao.getMovieByIdFromLocal(id = id)
    }
}