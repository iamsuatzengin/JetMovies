package com.suatzengin.whatshouldiwatch.data.repository

import com.suatzengin.whatshouldiwatch.common.Resource
import com.suatzengin.whatshouldiwatch.data.local.WatchListDao
import com.suatzengin.whatshouldiwatch.data.local.entities.MovieEntity
import com.suatzengin.whatshouldiwatch.domain.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override fun getWatchList(): Flow<Resource<List<MovieEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val watchList = dao.getWatchList()
            emit(Resource.Success(data = watchList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "error"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieById(id: Int): MovieEntity? {
        return dao.getMovieByIdFromLocal(id = id)
    }
}