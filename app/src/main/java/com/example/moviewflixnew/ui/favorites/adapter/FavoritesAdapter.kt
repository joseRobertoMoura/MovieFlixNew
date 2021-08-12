package com.example.moviewflixnew.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieflix.model.helper.ClickItemListener
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.model.MoviesModel

class FavoritesAdapter(
    private var list: MutableList<MoviesModel?>,
    private var listener: ClickItemListener,
    private var listenerCard: ClickItemListener
) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    companion object{
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_item, parent, false)
        return FavoritesViewHolder(view,list,listener, listenerCard)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class FavoritesViewHolder(
        itemView: View,
        private var list: MutableList<MoviesModel?>,
        private var listener: ClickItemListener,
        private var listenerCard: ClickItemListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvTitl: AppCompatTextView = itemView.findViewById(R.id.movieTitle)
        private val image: AppCompatImageView = itemView.findViewById(R.id.movieImage)
        private val imgFavorite: AppCompatImageView = itemView.findViewById(R.id.ic_favorite)

        fun bind(movie: MoviesModel?) {
            itemView.setOnClickListener {
                list[adapterPosition]?.let { card -> listenerCard.ClickItemMovie((card)) }
            }
            imgFavorite.setOnClickListener {
                list[adapterPosition]?.let { favorite -> listener.ClickItemMovie(favorite) }
            }
            if (movie?.original_title != null) {
                tvTitl.text = movie.original_title
            } else if (movie?.original_name != null) {
                tvTitl.text = movie.original_name
            }
            image.load( IMAGE_URL + movie?.poster_path) {
                placeholder(R.drawable.ic_baseline_image_24)
                fallback(R.drawable.ic_baseline_image_24)
            }
        }
    }
}