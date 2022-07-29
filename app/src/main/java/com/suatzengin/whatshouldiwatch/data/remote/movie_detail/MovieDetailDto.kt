package com.suatzengin.whatshouldiwatch.data.remote.movie_detail

import com.google.gson.annotations.SerializedName
import com.suatzengin.whatshouldiwatch.data.remote.genre.Genre
import com.suatzengin.whatshouldiwatch.data.remote.movie.MovieResponse
import com.suatzengin.whatshouldiwatch.domain.model.MovieDetail

data class MovieDetailDto(
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection?,

    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,

    @SerializedName("imdb_id")
    val imdbId: String?,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    val overview: String?,
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,

    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,

    @SerializedName("release_date")
    val releaseDate: String,

    val revenue: Int,
    val runtime: Int?,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<Language>,

    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int,

    val similar: MovieResponse,
    val credits: Credit
)

fun MovieDetailDto.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id = id,
        backdropPath = backdropPath,
        genres = genres,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        runtime = runtime,
        spokenLanguages = spokenLanguages,
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        similar = similar,
        credit = credits
    )
}