package com.example.movie.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.ui.main.model.Movie
import com.example.movie.ui.main.viewmodel.MainActivityViewModel
import com.example.movie.utils.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity() : AppCompatActivity(), SearchView.OnQueryTextListener {

    lateinit var recyclerViewAdapter: ContentListAdapter
    lateinit var progressBar: ProgressBar
    lateinit var view: View
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }



    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.post_list)
        progressBar = findViewById(R.id.progress_bar)
        view = findViewById(R.id.view)
        search_recycler_view.layoutManager =
            LinearLayoutManager(this)
        viewModel.getPostList()
        viewModel.publishObject.subscribe {
            search_text.gone()
            search_recycler_view.visible()
            if (response.size == 0) {
                if (it.size > 0) {
                    searchResponse.clear()
                    searchResponse = response
                    setAdapter(searchResponse)
                    recyclerViewAdapter.notifyDataSetChanged()
                    response.clear()
                    response.addAll(it)
                } else {
                    search_text.visible()
                    progressBar.gone()
                    Toast.makeText(this, getString(R.string.no_records_found), Toast.LENGTH_LONG)
                        .show()
                }

            }

        }

    }

    private var response: ArrayList<Movie?> = ArrayList()
    private var searchResponse: ArrayList<Movie?> = ArrayList()

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    private fun setAdapter(response: ArrayList<Movie?>) {
        progressBar.visibility = View.GONE
        recyclerViewAdapter =
            ContentListAdapter(response)
        search_recycler_view.adapter = recyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (!newText.isNullOrEmpty()){
            searchResponse = response.filter { it?.userId.toString() == newText } as ArrayList<Movie?>
            setAdapter(searchResponse)
        }
        return false
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search =
            menu?.findItem(R.id.action_search)?.actionView as SearchView
        search.setOnQueryTextListener(this)
        return true
    }


}
