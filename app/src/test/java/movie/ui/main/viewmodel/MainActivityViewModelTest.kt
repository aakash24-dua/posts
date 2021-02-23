package com.example.movie.ui.main.viewmodel

import com.example.movie.ui.main.model.Movie
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class MainActivityViewModelTest {

    lateinit var viewmodel: MainActivityViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewmodel = MainActivityViewModel()
    }

    @Test
    fun getMovieList() {
        runBlocking {
            launch(Dispatchers.Main) {
                viewmodel.getMovieList("", 0)
                delay(3000)
            }
        }
    }

    @Test
    fun updateData() {
        viewmodel.publishObject = PublishSubject.create<ArrayList<Movie?>>()
        viewmodel.updateData(arrayListOf(Movie("", "", "", "", "")), 0)

    }

    val queryListSizeGreaterThan10 =
        arrayListOf<String>("ss", "sd", "sdd", "we", "ed", "xss", "xxx", "xwxx", "xxxxx", "wee")
    val queryList = arrayListOf<String>("ss", "sd", "sdd", "we")

    @Test
    fun addToQueryListWhenGreaterThan10() {
        viewmodel.queryList = queryListSizeGreaterThan10
        viewmodel.addToQueryList("query")
        assertEquals(queryListSizeGreaterThan10.contains("ss"), false)
    }

    @Test
    fun addToQueryListWhenNotContains() {
        viewmodel.queryList = queryList
        viewmodel.addToQueryList("query")
        assertEquals(queryList.contains("query"), true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}