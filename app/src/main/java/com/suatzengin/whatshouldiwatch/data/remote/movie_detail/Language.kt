package com.suatzengin.whatshouldiwatch.data.remote.movie_detail

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val iso: String,
    val name: String,
)
