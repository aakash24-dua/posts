package com.example.movie.ui.movieDetail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.App
import com.example.movie.ui.movieDetail.model.PostDataResponse
import com.example.movie.utils.NetworkService
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostDetailViewmodel : ViewModel() {

    @Inject
    lateinit var service: NetworkService
    var publishObject = PublishSubject.create<PostDataResponse>()


    suspend fun getDetails(id: String): PostDataResponse? {
        try {
            return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                service.getDetails(id)
            }
        } catch (e: Exception) {
            return null
            Log.e("exception", "")
        }
    }

    fun getMovieDetails(id:String) {
        viewModelScope.launch{
            val it = getDetails(id)

            if (it != null){
                updateData(it)
            }
            else {
                publishObject.onError(Throwable("Response Not found"))
            }
        }
    }

    private fun updateData(it: PostDataResponse) {
        publishObject.onNext(it )
    }


    init {
        App().component.inject(this)
    }

}