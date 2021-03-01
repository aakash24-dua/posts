package com.example.movie.ui.movieDetail.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.ui.movieDetail.viewmodel.MovieDetailViewmodel
import kotlinx.android.synthetic.main.detail_view.*

class MovieDetailActivity : AppCompatActivity() {


    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MovieDetailViewmodel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_view)
        title = "Movie Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.extras?.getInt("imdbId",0)
        viewModel.getMovieDetails(id.toString()?:"")

        viewModel.publishObject.subscribe ({

            item_category_txt.text = it?.name?:""
            item_duration_txt.text = it?.email?:""
            item_rating_txt.text = it?.phone?:""

            item_synopsis_desc.text = it?.username?:""
            item_score_desc_txt.text = it?.website?:""
            item_reviews_desc_txt.text = it?.address.toString()?:""
            item_popularity_desc_txt.text = it?.company.toString()?:""

            item_director_desc_view.text = it?.id.toString()?:""
           // item_writer_desc_view.text = it?.?:""
          //  item_actor_desc_view.text = it?.actors?:""

        },{
            Toast.makeText(this,"Error in response",Toast.LENGTH_LONG).show()
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)

    }
}