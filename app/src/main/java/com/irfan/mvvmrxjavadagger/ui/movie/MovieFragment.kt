package com.irfan.mvvmrxjavadagger.ui.movie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.irfan.mvvmrxjavadagger.R
import com.irfan.mvvmrxjavadagger.common.ResultStatus
import com.irfan.mvvmrxjavadagger.model.Movie
import com.irfan.mvvmrxjavadagger.ui.main.model.Genre
import com.irfan.mvvmrxjavadagger.ui.main.viewmodel.MainViewModel
import com.irfan.mvvmrxjavadagger.ui.movie.adapter.MovieAdapter
import com.irfan.mvvmrxjavadagger.util.SharedPreferenceHelper
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_movie.view.*
import java.lang.reflect.Type
import javax.inject.Inject


class MovieFragment : Fragment() {

    private lateinit var homeViewModel: MovieViewModel
    private lateinit var adapterMovie: MovieAdapter
    private lateinit var root: View
    private lateinit var layoutManagers: LinearLayoutManager
    private val viewModelActivity: MainViewModel by activityViewModels()

    private var dataMovie = arrayListOf<Movie>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var mSharedPref: SharedPreferenceHelper

    companion object{
        const val TAG_SAVE_RECYCLERVIEW = "DATA_RV"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_movie, container, false)
        setRecycleView()
        if (savedInstanceState != null){
            Log.e("datanyaRestore", "Masukrestore")
            savedInstanceState.getParcelableArrayList<Movie>(TAG_SAVE_RECYCLERVIEW)?.let {
                dataMovie.addAll(
                    it
                )
                Log.e("datanyaRestore", it.size.toString())
            }
            adapterMovie.notifyDataSetChanged()
        }
        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(TAG_SAVE_RECYCLERVIEW, dataMovie)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null){
            savedInstanceState.getParcelableArrayList<Movie>(TAG_SAVE_RECYCLERVIEW)?.let {
                dataMovie.addAll(
                    it
                )
                Log.e("datanyaRestore", it.size.toString())
            }
            adapterMovie.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.dataMovies.observe(viewLifecycleOwner, Observer {
            it?.let { resultState ->
                when (resultState.status) {
                    ResultStatus.LOADING -> {
                        Log.e("datanyaFragment", "LOADING")
                    }
                    ResultStatus.SUCCESS -> {
                        Log.e("datanyaFragment", resultState.data?.size.toString())
                        setDataMovie(resultState.data)
                    }
                    ResultStatus.ERROR -> {
                        Log.e("datanyaFragment", "ERROR")
                    }
                }
            }
        })
        if (mSharedPref.getDataGenre() != ""){
            val gson = Gson()
            val json: String? = mSharedPref.getDataGenre()
            val type: Type = object : TypeToken<ArrayList<Genre>>() {}.type
            val dataArray: ArrayList<Genre> = gson.fromJson(json, type)
            adapterMovie.setDataStaticGenre(dataArray)
            homeViewModel.getMovie("en-US", 1)
        }

        viewModelActivity.dataGenre.observe(viewLifecycleOwner, Observer {
            it?.let { resultState ->
                when(resultState.status) {
                    ResultStatus.LOADING ->{
                        Log.e("datanyaMain", "LOADING")
                    }
                    ResultStatus.SUCCESS ->{
                        homeViewModel.getMovie("en-US", 1)
                    }
                    ResultStatus.ERROR -> {
                        Log.e("datanyaMain", "ERROR")
                    }
                }
            }
        })
    }

    private fun setDataMovie(data: List<Movie>?) {
        data?.let { dataMovie.addAll(it) }
        adapterMovie.notifyDataSetChanged()
    }

    private fun setRecycleView() {
        adapterMovie = MovieAdapter(context, dataMovie)
        layoutManagers = LinearLayoutManager(activity)
        root.rv_movie.apply {
            layoutManager = layoutManagers
            adapter = adapterMovie
        }
    }
}