package com.example.moviewflixnew.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.data.repository.MovieFlixRepository
import com.example.moviewflixnew.model.MoviesModel
import com.example.moviewflixnew.model.MoviesTendencyModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListMoviesViewModel(private val repository: MovieFlixRepository):ViewModel() {
    val listOfResults: MutableList<MoviesModel> = arrayListOf()

    private var totalPages = MutableLiveData<String>()
    private var error = MutableLiveData<String>()
    private var list = MutableLiveData<List<MoviesModel>>()
    val moviesList: LiveData<List<MoviesModel>>
        get() = list
    val total: LiveData<String>
        get() = totalPages
    val errorApi:LiveData<String>
        get() = error

    fun init(numPage: String) {
        setList(numPage)
    }

    fun setList(numPage: String) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                repository.requestMoviesTendency(
                    numPage,
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

    private fun onRequestSuccess(tendency: MoviesTendencyModel) {
        totalPages.postValue(tendency.total_pages)
        tendency.result.forEach { listOfResults.add(it) }
        list.postValue(listOfResults)
    }
}