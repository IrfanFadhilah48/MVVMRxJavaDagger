package com.irfan.mvvmrxjavadagger.ui.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irfan.mvvmrxjavadagger.R
import com.irfan.mvvmrxjavadagger.model.Movie
import com.irfan.mvvmrxjavadagger.ui.main.model.Genre
import com.irfan.mvvmrxjavadagger.util.SharedPreferenceHelper
import com.irfan.mvvmrxjavadagger.util.parseDate
import kotlinx.android.synthetic.main.item_movie.view.*
import javax.inject.Inject

class MovieAdapter (val context: Context?, private var dataMovies: ArrayList<Movie>): RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    private var dataGenreStatic = arrayListOf<Genre>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = dataMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = dataMovies[position]

        holder.tvTitle.text = movie.title
        holder.tvRating.text = movie.voteAverage.toString()
        holder.tvTanggal.text = movie.releaseDate?.let { parseDate(it) }
        val childLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val genreAdapter = movie.genreIds?.let { GenreAdapter(it) }
        holder.rvGenre.apply {
            layoutManager = childLayoutManager
            adapter = genreAdapter
            adapter?.notifyDataSetChanged()
        }
        genreAdapter?.setDataGenre(dataGenreStatic)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.tv_title_list_movie
        val tvRating: TextView = itemView.tv_rating_list_movie
        val tvTanggal: TextView = itemView.tv_out_list_movie
        val ivMovie: ImageView = itemView.iv_list_movie
        val rvGenre: RecyclerView = itemView.rv_list_movie
    }

    fun setDataStaticGenre(dataGenre: List<Genre>){
        dataGenreStatic.addAll(dataGenre)
    }
}