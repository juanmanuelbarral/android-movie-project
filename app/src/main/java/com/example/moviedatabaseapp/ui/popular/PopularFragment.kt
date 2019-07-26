package com.example.moviedatabaseapp.ui.popular

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviedatabaseapp.R
import com.example.moviedatabaseapp.databinding.FragmentPopularBinding
import com.example.moviedatabaseapp.ui.movie.MovieFragment

class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private lateinit var viewModel: PopularViewModel
    private lateinit var adapter: PopularAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_popular,
            container,
            false
        )
        viewModel = ViewModelProviders.of(this).get(PopularViewModel::class.java)

        viewModel.popularMovies.observe(this, Observer { movies ->
            if (movies != null) {
                adapter.setData(movies)
            } else {
                Log.d("TestPopular", "Null movies")
            }
        })

        viewModel.navigateToMovie.observe(this, Observer { navigate ->
            if (navigate!!) {
                val newMovieFragment = MovieFragment()
//                newMovieFragment.setMovie(viewModel.movieForNavigation!!)

                fragmentManager!!.beginTransaction()
                    .replace(R.id.mainActivityContainer, newMovieFragment)
                    .addToBackStack(null)
                    .commit()

                viewModel.onNavigationToMovieDone()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PopularAdapter()
        adapter.setData(viewModel.popularMovies.value!!)
        adapter.setActionDelegate(viewModel)

        binding.popularRecyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(
            activity!!,
            2,
            androidx.recyclerview.widget.GridLayoutManager.HORIZONTAL,
            false
        )
        binding.popularRecyclerView.adapter = adapter
        (binding.popularRecyclerView.itemAnimator as androidx.recyclerview.widget.DefaultItemAnimator).supportsChangeAnimations = false

        viewModel.loadPopularMovies()
    }
}