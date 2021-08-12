package com.example.moviewflixnew.ui.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import coil.load
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.model.MoviesModel
import com.example.moviewflixnew.ui.MainActivity
import com.example.moviewflixnew.ui.favorites.FavoritesViewModel
import com.example.moviewflixnew.ui.utils.dialog.DialogMessageError
import kotlinx.android.synthetic.main.fragment_detail_movies.*
import javax.inject.Inject

class DetailMoviesFragment(private var movie:MoviesModel) : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val detailViewModel by viewModels<DetailMovieViewModel> {viewModelFactory}

    @Inject
    lateinit var viewModelFactoryFavorites: ViewModelProvider.Factory
    private val favoritesViewModel by viewModels<FavoritesViewModel> {viewModelFactoryFavorites}

    private lateinit var favoriteBtn:AppCompatImageView

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as Context
        initView(view)
        initEventClick(activity)
        bindViews(activity)
    }

    private fun initEventClick(context: Context) {
        favoriteBtn.setOnClickListener {
            favoritesViewModel.init(movie,context)
            Toast.makeText(context,"O filme foi adcionado a sua lista de favoritos!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initView(view: View) {
        favoriteBtn = view.findViewById(R.id.add_favorites)
    }

    companion object {
        fun newInstance(movie:MoviesModel) = DetailMoviesFragment(movie)
    }

    private fun bindViews(activity:Context) {
        detailViewModel.init(movie, activity)
        detailViewModel.detailsViewAction.observe(viewLifecycleOwner) { state ->
            when(state){
                is DetailsActionView.Success -> {
                    movieImage_detail.load(
                        "https://image.tmdb.org/t/p/w500"
                                + state.responseSucess?.poster_path
                    ) {
                        placeholder(R.drawable.ic_baseline_image_24)
                        fallback(R.drawable.ic_baseline_image_24)
                    }
                    if (state.responseSucess?.original_title !=  null) {
                        tv_title.text =
                            state.responseSucess?.original_title
                    } else if (state.responseSucess?.original_name != null) {
                        tv_title.text =
                            state.responseSucess?.original_name
                    }
                    tv_overview.text = state.responseSucess?.overview
                }

                is DetailsActionView.Error -> {
                        createDialog(state.error.toString())
                }

                else -> {
                    Toast.makeText(
                        activity,
                        "Ops tivemos um problema, tente novamente em alguns instantes.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

    private fun createDialog(message: String) {
        val dialog = DialogMessageError(message)
        dialog.show(parentFragmentManager,dialog.tag)
    }
}