package com.example.movie.utils

import com.example.movie.ui.main.model.Movie
import com.example.movie.ui.movieDetail.model.PostDataResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface SearchApi {

    @GET("/posts")
     suspend fun getPostList(
    ): ArrayList<Movie>?

    @GET("/users/{userId}")
    suspend fun getPostDetails(
        @Path("userId") id:String
    ): PostDataResponse?

}
