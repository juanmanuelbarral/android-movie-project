package com.example.moviedatabaseapp.ui.popular

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.moviedatabaseapp.R
import com.example.moviedatabaseapp.databinding.ItemMovieBinding
import com.example.moviedatabaseapp.model.data.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class PopularAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<PopularAdapter.PopularMovieViewHolderA>() {

    private var data: List<Movie> = arrayListOf()
    private var actionDelegate: PopularActionDelegate? = null

    fun setData(newData: List<Movie>) {
        this.data = newData
        notifyDataSetChanged()
    }

    fun setActionDelegate(delegate: PopularActionDelegate) {
        this.actionDelegate = delegate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolderA {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return PopularMovieViewHolderA(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolderA, position: Int) {
        val movie: Movie = data[position]
        holder.bind(movie)
    }

    inner class PopularMovieViewHolderA constructor(private val binding: ItemMovieBinding)
        : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        private var movie: Movie? = null

        init {
            itemView.setOnClickListener {
                Log.i("PopularAdapter", "on click listener")
                Log.i("PopularAdapter", "movie: ${movie?.title}")
                Log.i("PopularAdapter", "action delegate: ${actionDelegate.toString()}")
                if (movie != null && actionDelegate != null) {
                    actionDelegate!!.onClickMovie(movie!!)
                }
            }
        }

        fun bind(movie: Movie) {
            this.movie = movie

            val url = movie.posterUrl()
            Glide.with(binding.root.context).load(url).into(binding.itemMoviePosterImage)

            binding.itemMovieTitleLabel.text = movie.title
        }
    }

    inner class PopularMovieViewHolder(inflater: LayoutInflater, private var parent: ViewGroup)
        : androidx.recyclerview.widget.RecyclerView.ViewHolder(inflater.inflate(R.layout.item_movie, parent, false)) {

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

            val url = movie.posterUrl()
            Glide.with(parent.context).load(url).into(itemView.itemMoviePosterImage)

            itemView.itemMovieTitleLabel.text = movie.title
        }
    }
}