package com.example.movie.ui.main.view

import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.ui.main.model.Movie
import java.sql.Timestamp
import java.util.*


class ContentItemViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
    val id: TextView = root.findViewById(R.id.id_text_view)
    val userId: TextView = root.findViewById(R.id.userId_text_view)

    @RequiresApi(Build.VERSION_CODES.N)
    fun bind(response: Movie?) {
        id.text = response?.id.toString()
        userId.text = response?.userId.toString()
    }
}