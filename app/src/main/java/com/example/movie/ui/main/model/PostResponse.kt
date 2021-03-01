package com.example.movie.ui.main.model

import com.google.gson.annotations.SerializedName


data class MovieResponse(
	@field:SerializedName("data")
	val response: ArrayList<Movie?>? = null
){

}

data class Movie(

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("id")
	val id: Long? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null

	/*@field:SerializedName("Title")
	val title: String? = null*/
)
