package com.example.moviedatabaseapp.views

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviedatabaseapp.R
import com.example.moviedatabaseapp.adapters.PopularAdapter
import com.example.moviedatabaseapp.controllers.PopularController
import com.example.moviedatabaseapp.databinding.FragmentPopularBinding

class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private lateinit var adapter: PopularAdapter
    private val controller = PopularController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_popular,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PopularAdapter()
        adapter.setData(controller.popularMovies.value!!)
        adapter.setActionDelegate(controller)
        binding.popularRecyclerView.layoutManager = GridLayoutManager(
            activity!!,
            2,
            GridLayoutManager.HORIZONTAL,
            false
        )
        binding.popularRecyclerView.adapter = adapter
        (binding.popularRecyclerView.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false

        controller.popularMovies.observe(this, Observer { movies ->
            if (movies != null) {
                adapter.setData(movies)
            } else {
                Log.d("TestPopular", "Null movies")
            }
        })

        controller.navigateToMovie.observe(this, Observer { navigate ->
            if (navigate!!) {
                val newMovieFragment = MovieFragment()
                newMovieFragment.setMovie(controller.movieForNavigation!!)

                fragmentManager!!.beginTransaction()
                    .replace(R.id.mainActivityContainer, newMovieFragment)
                    .addToBackStack(null)
                    .commit()

                controller.onNavigationToMovieDone()
            }
        })

        controller.loadPopularMovies()
    }
}