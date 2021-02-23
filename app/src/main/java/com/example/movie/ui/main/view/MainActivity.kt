package com.example.movie.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie.R
import com.example.movie.ui.main.model.Movie
import com.example.movie.ui.main.viewmodel.MainActivityViewModel
import com.example.movie.utils.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    lateinit var recyclerViewAdapter: ContentListAdapter
    lateinit var progressBar: ProgressBar
    lateinit var view: View
    lateinit var horizontalScrollView: HorizontalScrollView
    lateinit var horizontalLinearLayout: LinearLayout
    lateinit var recentItemsTitle: TextView
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    private lateinit var listener: EndlessRecyclerScrollListener


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.movie_list)
        progressBar = findViewById(R.id.progress_bar)
        view = findViewById(R.id.view)
        horizontalLinearLayout = findViewById(R.id.horizontal_linear)
        horizontalScrollView = findViewById(R.id.horizontal_scroll)
        recentItemsTitle = findViewById(R.id.recent_items_title)
        horizontalScrollView.visibility = View.GONE
        recentItemsTitle.visibility = View.GONE
        search_recycler_view.layoutManager =
            GridLayoutManager(this, 2)


        listener = EndlessRecyclerScrollListener(
            search_recycler_view.layoutManager as GridLayoutManager,
            loadMore!!
        )
        search_recycler_view.addOnScrollListener(listener)

        viewModel.publishObject.subscribe {
            search_text.gone()
            search_recycler_view.visible()
            if (response.size == 0) {
                if (it.size > 0) {
                    setAdapter(response)
                    recyclerViewAdapter.notifyDataSetChanged()
                    response.clear()
                    response.addAll(it)
                } else {
                    search_text.visible()
                    progressBar.gone()
                    Toast.makeText(this, getString(R.string.no_records_found), Toast.LENGTH_LONG).show()
                }

            } else {
                hideLoadingItem()
                response.addAll(it)
                recyclerViewAdapter.notifyDataSetChanged()
            }

        }

    }

    private val loadMore: (Int) -> Unit = { current_page ->
        showLoadingItem()
        viewModel.getMovieList(queryGlobal, current_page)
    }

    private fun showLoadingItem() {
        val adapter = search_recycler_view.adapter
        if (adapter is ContentListAdapter) adapter.showLoadingItem()
    }

    private fun hideLoadingItem() {
        val adapter = search_recycler_view.adapter
        if (adapter is ContentListAdapter) adapter.hideLoading()
    }

    private fun resetListener() {
        listener.reset()
    }

    private var response: ArrayList<Movie?> = ArrayList()

    private var queryGlobal = ""
    override fun onQueryTextSubmit(query: String?): Boolean {
        progressBar.visible()
        view.visible()

        if (!query.isNullOrBlank() && query.length > 1) {
            queryGlobal = query
            response.clear()
            resetListener()
            if (NetworkUtils.isNetworkConnected(this)) {
                viewModel.addToQueryList(query)
                search_text.gone()
                viewModel.getMovieList(query, 1)
            } else {
                progressBar.gone()
                view.gone()
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
            }

        } else {
            search_text.visible()
            search_recycler_view.gone()
            progressBar.gone()
        }
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
        horizontalScrollView.setVisible(newText.isNullOrEmpty())
        recentItemsTitle.setVisible(newText.isNullOrEmpty())
        if (newText.isNullOrEmpty()) {
            horizontalLinearLayout.removeAllViews()
            for (i in 0 until viewModel.queryList.size) {
                val text = TextView(this)
                text.myTextView(viewModel.queryList[i])
                text.setOnClickListener {
                    onQueryTextSubmit(text.text.toString())
                }
                horizontalLinearLayout.addView(text)
            }
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
