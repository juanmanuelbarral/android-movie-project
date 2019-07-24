package com.example.moviedatabaseapp.views

import android.arch.lifecycle.Observer
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
import kotlinx.android.synthetic.main.fragment_popular.*

class PopularFragment: Fragment() {

    private val controller = PopularController()
    private lateinit var adapter: PopularAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PopularAdapter()
        adapter.setData(controller.popularMovies.value!!)
        adapter.setActionDelegate(controller)
        popularRecyclerView.layoutManager = GridLayoutManager(activity!!, 2, GridLayoutManager.HORIZONTAL, false)
        popularRecyclerView.adapter = adapter
        (popularRecyclerView.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false

        controller.popularMovies.observe(this, Observer {movies ->
            if (movies != null) {
                adapter.setData(movies)
            } else {
                Log.d("TestPopular", "Null movies")
            }
        })

        controller.loadPopularMovies()
    }
}