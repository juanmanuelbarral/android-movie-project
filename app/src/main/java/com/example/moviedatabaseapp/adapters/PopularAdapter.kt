package com.example.moviedatabaseapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.moviedatabaseapp.R
import com.example.moviedatabaseapp.model.data.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class PopularAdapter: RecyclerView.Adapter<PopularAdapter.PopularMovieViewHolder>() {

    private var data: List<Movie> = arrayListOf()
    private var actionDelegate: PopularActionDelegate? = null

    fun setData(newData: List<Movie>) {
        this.data = newData
        notifyDataSetChanged()
    }

    fun setActionDelegate(delegate: PopularActionDelegate) {
        this.actionDelegate = delegate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PopularMovieViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        val movie: Movie = data[position]
        holder.bind(movie)
    }

    inner class PopularMovieViewHolder(inflater: LayoutInflater, private var parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_movie, parent, false)) {

        private var movie: Movie? = null

        init {
            itemView.setOnClickListener {
                if (movie != null && actionDelegate != null) {
                    actionDelegate!!.onClickMovie(movie!!)
                }
            }
        }

        fun bind(movie: Movie) {
            this.movie = movie

            val baseUrlPhoto = "https://image.tmdb.org/t/p/w500"
            val url = baseUrlPhoto + movie.posterPath
            Glide.with(parent.context).load(url).into(itemView.itemMoviePosterImage)

            itemView.itemMovieTitleLabel.text = movie.title
        }
    }
}