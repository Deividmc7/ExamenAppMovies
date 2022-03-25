package com.deividsalmeron.examenapp.request

import android.util.Log
import com.deividsalmeron.examenapp.models.Movie
import com.deividsalmeron.examenapp.response.GetMoviesResponse
import com.deividsalmeron.examenapp.utils.MovieApiDetails
import com.deividsalmeron.examenapp.utils.MovieApiLatest
import com.deividsalmeron.examenapp.utils.MovieApiPopular
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepository {

    private val api: MovieApiPopular
    private val apiLatest: MovieApiLatest
    private val apiDetails: MovieApiDetails

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MovieApiPopular::class.java)
        apiLatest = retrofit.create(MovieApiLatest::class.java)
        apiDetails = retrofit.create(MovieApiDetails::class.java)
    }

    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getLatesMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        apiLatest.getLatestMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getDetailMovie(
        movie_id: Long,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        apiDetails.getDetailsMovie(movie_id)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            Log.i("Respuesta", responseBody.toString())
//                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}
