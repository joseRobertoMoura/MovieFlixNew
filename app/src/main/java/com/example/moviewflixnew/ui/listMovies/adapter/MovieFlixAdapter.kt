package com.example.moviewflixnew.ui.listMovies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieflix.model.helper.ClickItemListener
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.model.MoviesModel

class MovieFlixAdapter(private var list: List<MoviesModel>, private var listener: ClickItemListener) :
    RecyclerView.Adapter<MovieFlixAdapter.MoviesViewHolder>() {

    companion object{
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(view, list, listener)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

   inner class MoviesViewHolder(
        itemView: View,
        private var list: List<MoviesModel>,
        private var listener: ClickItemListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvTitl: AppCompatTextView = itemView.findViewById(R.id.movieTitle)
        private val image: AppCompatImageView = itemView.findViewById(R.id.movieImage)

        fun bind(movie: MoviesModel) {
            itemView.setOnClickListener {
                listener.ClickItemMovie(list[adapterPosition])
            }
            if (movie.original_title != null) {
                tvTitl.text = movie.original_title
            } else if (movie.original_name != null) {
                tvTitl.text = movie.original_name
            }
            image.load( IMAGE_URL + movie.poster_path) {
                placeholder(R.drawable.ic_baseline_image_24)
                fallback(R.drawable.ic_baseline_image_24)
            }

        }
    }


}