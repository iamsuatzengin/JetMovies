package com.suatzengin.whatshouldiwatch.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_movie")
data class MovieEntity(
    @PrimaryKey val movieId: Int,
    val title: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    val runtime: Int?,
    val overview: String?
)
