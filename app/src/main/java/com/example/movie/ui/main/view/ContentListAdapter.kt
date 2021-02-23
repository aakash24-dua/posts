package com.example.movie.ui.main.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.ui.main.model.Movie
import com.example.movie.ui.movieDetail.view.MovieDetailActivity

class ContentListAdapter(private val data: ArrayList<Movie?>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val LOADING_VIEW = 1
    val ITEM_VIEW = 0

    private var onLoadMoreListener: MainActivity.OnLoadMoreListener? = null
    fun setOnLoadMoreListener(mOnLoadMoreListener: MainActivity.OnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener
    }

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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContentItemViewHolder){
            (holder).bind(data[position])
            holder.root.setOnClickListener{
                val intent = Intent(holder.root.context,MovieDetailActivity::class.java)
                intent.putExtra("imdbId",data[position]?.imdbID)
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

    fun showLoadingItem() {
        if (data.get(itemCount - 1) != null) {
            data.add(null)
            notifyItemInserted(itemCount - 1)
        }
    }

    fun hideLoading() {
        val lastItem = itemCount - 1
        if (data.get(lastItem) == null) {
            data.removeAt(lastItem)
            notifyItemRemoved(lastItem)
        }
    }



}
