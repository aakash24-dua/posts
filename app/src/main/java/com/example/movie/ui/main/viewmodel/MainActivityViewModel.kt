package com.example.movie.ui.main.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.App
import com.example.movie.utils.NetworkService
import com.example.movie.ui.main.model.Movie
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.*
import javax.inject.Inject


 class MainActivityViewModel(
) : ViewModel() {
    @Inject
    lateinit var service: NetworkService
    var publishObject = PublishSubject.create<ArrayList<Movie>>()
    suspend fun getList(): ArrayList<Movie>? {
        try{
       val data =  viewModelScope.async(Dispatchers.IO){
            service.getList()
        }.await()
        return data?:ArrayList()
        }
        catch (e:Exception){
            return ArrayList()
        }
    }

     fun getPostList() {
       viewModelScope.launch{
           val it = getList()
           it?.let {
               updateData(it)
           }
       }
    }

    fun updateData(it: ArrayList<Movie>) {
        publishObject.onNext(it )
    }



    init {
        App().component.inject(this)
    }
}