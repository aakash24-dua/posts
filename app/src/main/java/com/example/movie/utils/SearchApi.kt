package com.example.movie.utils

import com.example.movie.ui.main.model.MovieResponse
import com.example.movie.ui.movieDetail.model.MovieDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SearchApi {


    @GET("?apikey=b9bd48a6&type=movie")
     suspend fun getMovieList(
        @Query("s") s:String,
        @Query("page") page:Int
    ): MovieResponse?

    @GET("?apikey=b9bd48a6")
    suspend fun getMovieDetails(
        @Query("i") id:String
    ): MovieDetailsResponse?

}
