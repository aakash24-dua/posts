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

        val id = intent.extras?.getString("imdbId","")
        viewModel.getMovieDetails(id?:"")

        viewModel.publishObject.subscribe ({
            Glide.with(this).load(it?.poster?:"")
                .placeholder(R.drawable.ic_launcher_foreground).into(item_image_view)
            item_category_txt.text = it?.title?:""
            item_duration_txt.text = it?.runtime?:""
            item_rating_txt.text = it?.ratings?.get(0)?.value?:""

            item_synopsis_desc.text = it?.plot?:""
            item_score_desc_txt.text = it?.metascore?:""
            item_reviews_desc_txt.text = it?.imdbVotes?:""
            item_popularity_desc_txt.text = it?.awards?:""

            item_director_desc_view.text = it?.director?:""
            item_writer_desc_view.text = it?.writer?:""
            item_actor_desc_view.text = it?.actors?:""

        },{
            Toast.makeText(this,"Error in response",Toast.LENGTH_LONG).show()
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)

    }
}