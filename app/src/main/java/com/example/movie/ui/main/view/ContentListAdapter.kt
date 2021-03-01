package com.example.movie.ui.main.view

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.ui.main.model.Movie
import com.example.movie.ui.movieDetail.view.PostDetailActivity

class ContentListAdapter(private val data: ArrayList<Movie?>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val LOADING_VIEW = 1
    val ITEM_VIEW = 0

    private var onLoadMoreListener: MainActivity.OnLoadMoreListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType.equals(ITEM_VIEW)){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)

            return ContentItemViewHolder(
                view
            )
        }
        else {
            return LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_progress,
                    parent,
                    false
                )
            )

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContentItemViewHolder){
            (holder).bind(data[position])
            holder.root.setOnClickListener{
                val intent = Intent(holder.root.context,PostDetailActivity::class.java)
                intent.putExtra("imdbId",data[position]?.userId)
                holder.root.context.startActivity(intent)
            }
        }

    }

    private inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var progressBar: ProgressBar

        init {
            progressBar = view.findViewById<View>(R.id.active_lead_item_progress_bar) as ProgressBar
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (data.get(position) == null)
            LOADING_VIEW
        else {
            ITEM_VIEW
        }
    }



}
