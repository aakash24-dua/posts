package com.example.movie.ui.main.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.App
import com.example.movie.utils.NetworkService
import com.example.movie.ui.main.model.Movie
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.*
import javax.inject.Inject


open class MainActivityViewModel : ViewModel() {
    @Inject
    lateinit var service: NetworkService
    var publishObject = PublishSubject.create<ArrayList<Movie?>>()

     var queryList = arrayListOf<String>()

    suspend fun getList(search:String,start: Int): ArrayList<Movie?>? {
        try{
       val data =  viewModelScope.async(Dispatchers.IO){
            service.getMovieList(search, start)
        }.await()
        return data?.response?:ArrayList()
        }
        catch (e:Exception){
            return ArrayList()
        }
    }

     fun getMovieList(search:String,start:Int) {
       viewModelScope.launch{
           val it = getList(search,start)
           it?.let {
               updateData(it,start)
           }
       }
    }

    fun updateData(it: ArrayList<Movie?>, offset: Int) {
        publishObject.onNext(it )
    }


    fun addToQueryList(query:String){
        if (queryList.size >= 10) {
            queryList.removeAt(0)
        }
        if (!queryList.contains(query))queryList.add(query)

    }


    init {
        App().component.inject(this)
    }
}