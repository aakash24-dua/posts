package com.example.movie.ui.main.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.ui.main.model.Movie


class ContentItemViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
    val title: TextView = root.findViewById(R.id.title_text_view)
    val image: ImageView = root.findViewById(R.id.image_view)
    val releaseDate: TextView = root.findViewById(R.id.release_date_text_view)

    fun bind(response: Movie?) {
        title.text = response?.title?:"Default Movie"
        Glide.with(root.context).load(response?.poster?:"")
            .placeholder(R.drawable.ic_launcher_foreground).into(image)
        releaseDate.text = response?.year

    }


}