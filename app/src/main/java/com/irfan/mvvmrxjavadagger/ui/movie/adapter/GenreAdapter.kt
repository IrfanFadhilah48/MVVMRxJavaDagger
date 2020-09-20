package com.irfan.mvvmrxjavadagger.ui.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.irfan.mvvmrxjavadagger.R
import com.irfan.mvvmrxjavadagger.ui.main.model.Genre
import com.irfan.mvvmrxjavadagger.util.SharedPreferenceHelper
import kotlinx.android.synthetic.main.item_genre.view.*
import java.lang.reflect.Type
import javax.inject.Inject

class GenreAdapter (private val dataGenres: ArrayList<Int>): RecyclerView.Adapter<GenreAdapter.ViewHolder>(){

    private var dataStaticGenre = arrayListOf<Genre>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (dataGenres.size >= 4){
            4
        }
        else{
            dataGenres.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataGenres[position]
        dataStaticGenre.forEach {
            if (it.id == data){
                holder.tvGenre.text = it.name
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGenre: TextView = itemView.tv_title_list_genre
    }

    fun setDataGenre(dataGenres: List<Genre>){
        dataStaticGenre.addAll(dataGenres)
    }
}