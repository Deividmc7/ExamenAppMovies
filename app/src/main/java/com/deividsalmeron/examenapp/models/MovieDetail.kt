package com.deividsalmeron.examenapp.models

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("vote_count") val voteCount: Float,
    @SerializedName("release_date") val releaseDate: String
)
