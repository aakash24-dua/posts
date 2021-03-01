package com.example.movie.ui.movieDetail.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.movie.R
import com.example.movie.ui.movieDetail.viewmodel.PostDetailViewmodel
import kotlinx.android.synthetic.main.detail_view.*

class PostDetailActivity : AppCompatActivity() {


    private val viewModel by lazy {
        ViewModelProviders.of(this).get(PostDetailViewmodel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_view)
        title = "Post Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.extras?.getInt("imdbId",0)
        viewModel.getMovieDetails(id.toString())

        viewModel.publishObject.subscribe ({

            item_category_txt.text = "Name - ".plus(it?.name?:"")
            item_duration_txt.text = "Email - ".plus(it?.email?:"")
            item_rating_txt.text = "Mobile- ".plus(it?.phone?:"")

            item_synopsis_desc.text = it?.username?:""
            item_score_desc_txt.text = it?.website?:""
            item_reviews_desc_txt.text = it?.address.toString()
            item_popularity_desc_txt.text = it?.company.toString()

            item_director_desc_view.text = it?.id.toString()

        },{
            Toast.makeText(this,"Error in response",Toast.LENGTH_LONG).show()
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)

    }
}