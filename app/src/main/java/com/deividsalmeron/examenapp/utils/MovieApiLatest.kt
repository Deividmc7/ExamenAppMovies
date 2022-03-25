package com.deividsalmeron.examenapp.utils

import com.deividsalmeron.examenapp.response.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiLatest {
    @GET("movie/top_rated")
    fun getLatestMovies(
        @Query("api_key") apiKey: String = Credentials.APIKEY,
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}