package com.suatzengin.whatshouldiwatch.data.remote

import com.suatzengin.whatshouldiwatch.common.Constants
import com.suatzengin.whatshouldiwatch.data.remote.genre.GenreResponse
import com.suatzengin.whatshouldiwatch.data.remote.movie.MovieResponse
import com.suatzengin.whatshouldiwatch.data.remote.movie_detail.MovieDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET(Constants.TOP_RATED)
    suspend fun getTopRatedMovies(
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("language") language: String = "tr-TR",
        @Query("page") page: Int
    ): MovieResponse

    @GET(Constants.GENRE_MOVIE)
    suspend fun getGenres(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "tr-TR"
    ): GenreResponse

    @GET(Constants.POPULAR)
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("language") language: String = "tr-TR",
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "tr-TR",
        @Query("append_to_response") appendToResponse: String = "credits,similar"
    ): MovieDetailDto

    @GET(Constants.DISCOVER)
    suspend fun getMovieWithGenres(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "tr-TR",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int,
    ): MovieResponse

    @GET(Constants.SEARCH_MOVIE)
    suspend fun searchMovie(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "tr-TR",
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieResponse
}