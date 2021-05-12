package com.example.moviewflixnew.viewModel

import android.content.Context
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
    private var error = MutableLiveData<String>()
    val moviesDetail: LiveData<DetailModel>
        get() = detailObject
    val errorApi:LiveData<String>
        get() = error

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
        when {
            code != null -> {
                error.postValue(code.toString())
            }
            message?.isNotEmpty() == true -> {
                error.postValue("Ops,tivemos um problema. Verifique sua conexão com a internet e " +
                        "tente Novamente em alguns instantes!")
            }
        }
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