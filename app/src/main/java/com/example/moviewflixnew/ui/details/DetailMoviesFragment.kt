package com.example.moviewflixnew.ui.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import coil.load
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.model.MoviesModel
import com.example.moviewflixnew.ui.MainActivity
import com.example.moviewflixnew.ui.dialog.DialogMessageError
import kotlinx.android.synthetic.main.fragment_detail_movies.*
import javax.inject.Inject

class DetailMoviesFragment(private var movie:MoviesModel) : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val detailViewModel by viewModels<DetailMovieViewModel> {viewModelFactory}

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
        bindViews(activity)
    }
    companion object {
        fun newInstance(movie:MoviesModel) = DetailMoviesFragment(movie)
    }

    private fun bindViews(activity:Context) {
        detailViewModel.init(movie, activity)
        detailViewModel.moviesDetail.observe(viewLifecycleOwner) {
            if (it != null){
                movieImage_detail.load(
                    "https://image.tmdb.org/t/p/w500"
                            + it.poster_path
                ) {
                    placeholder(R.drawable.ic_baseline_image_24)
                    fallback(R.drawable.ic_baseline_image_24)
                }
                if (it.original_title !=  null) {
                    tv_title.text =
                        it.original_title
                } else if (it.original_name != null) {
                    tv_title.text =
                        it.original_name
                }
                tv_overview.text = it.overview
            }else {
                Toast.makeText(
                    activity,
                    "Ops tivemos um problema, tente novamente em alguns instantes.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        detailViewModel.errorApi.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                createDialog(it)
            }
        }

    }

    private fun createDialog(message: String) {
        val dialog = DialogMessageError(message)
        dialog.show(parentFragmentManager,dialog.tag)
    }
}