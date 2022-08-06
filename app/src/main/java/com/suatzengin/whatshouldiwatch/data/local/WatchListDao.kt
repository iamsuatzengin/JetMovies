package com.suatzengin.whatshouldiwatch.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.suatzengin.whatshouldiwatch.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WatchListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Delete
    suspend fun delete(movie: MovieEntity)

    @Query("SELECT * FROM tbl_movie")
    fun getWatchList(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tbl_movie WHERE movieId = :id")
    suspend fun getMovieByIdFromLocal(id: Int): MovieEntity?
}