package com.deividsalmeron.examenapp.utils

import com.deividsalmeron.examenapp.response.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiDetails {
    @POST("movie/{movie_id}?")
    fun getDetailsMovie(
        @Path("movie_id") movie_id: Long,
        @Query("api_key") apiKey: String = Credentials.APIKEY
    ): Call<GetMoviesResponse>
}