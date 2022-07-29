package com.suatzengin.whatshouldiwatch.data.repository

import com.suatzengin.whatshouldiwatch.common.Resource
import com.suatzengin.whatshouldiwatch.data.remote.ApiService
import com.suatzengin.whatshouldiwatch.data.remote.genre.GenreResponse
import com.suatzengin.whatshouldiwatch.data.remote.movie.MovieResponse
import com.suatzengin.whatshouldiwatch.data.remote.movie_detail.toMovieDetail
import com.suatzengin.whatshouldiwatch.domain.model.HomeType
import com.suatzengin.whatshouldiwatch.domain.model.MovieDetail
import com.suatzengin.whatshouldiwatch.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val api: ApiService,
) : NetworkRepository {

    override suspend fun getTopRatedMovies(page: Int): MovieResponse {
        return api.getTopRatedMovies(page = page)
    }

    override suspend fun getGenres(): GenreResponse {
        return api.getGenres()
    }

    override suspend fun getPopularMovies(page: Int): MovieResponse {
        return api.getPopularMovies(page = page)
    }


    override suspend fun getMovieWithGenres(page: Int, genreId: Int): MovieResponse {
        return api.getMovieWithGenres(page = page, genreId = genreId)
    }

    override suspend fun searchMovie(page: Int, query: String): MovieResponse {
        return api.searchMovie(query = query, page = page)
    }

    override fun getMovieById(id: Int): Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Loading())
        try {
            val movie = api.getMovieDetail(movieId = id).toMovieDetail()
            emit(Resource.Success(movie))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage  ?: "Error"))
        }
    }


    override fun getHomeMovies(): Flow<Resource<List<HomeType>>> = flow {
        emit(Resource.Loading())
        try {
            val popular = api.getPopularMovies(page = 1).results
            val topRated = api.getTopRatedMovies(page = 1).results

            val list = listOf(HomeType.TopRated(topRated), HomeType.Popular(popular))

            emit(Resource.Success(list))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}




