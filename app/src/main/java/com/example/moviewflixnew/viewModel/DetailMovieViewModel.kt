package com.example.moviewflixnew.viewModel

import android.content.Context
import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.data.repository.MovieFlixRepository
import com.example.moviewflixnew.model.DetailModel
import com.example.moviewflixnew.model.MoviesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailMovieViewModel(private var repository: MovieFlixRepository):ViewModel() {

    private var detailObject = MutableLiveData<DetailModel>()
    val moviesDetail: LiveData<DetailModel>
        get() = detailObject

    fun init(movie: MoviesModel?, context: Context) {
        val id: String? = movie?.id
        setObject(id, context)
    }

    private fun setObject(id: String?, context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                repository.requestMovieDetails(
                    id,
                    ::onRequestSuccess,
                    ::onRequestError
                )
            }
        }
    }

    private fun onRequestError(code: Int?, message: String?) {
        detailObject.postValue(null)
    }

    private fun onRequestSuccess(movieDetail: DetailModel) {

        val detail = DetailModel(
            movieDetail.original_title.toString(),
            movieDetail.poster_path.toString(),
            movieDetail.overview.toString(),
            movieDetail.original_name.toString()
        )

        detailObject.postValue(detail)

    }
}