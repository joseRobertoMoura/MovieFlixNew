package com.example.moviewflixnew.ui.listMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.data.model.MoviesTendencyResponse
import com.example.moviewflixnew.usecase.tendency.GetListTendencyUseCaseImp
import com.example.moviewflixnew.data.model.MoviesModel
import kotlinx.coroutines.*
import javax.inject.Inject

class ListMoviesViewModel @Inject constructor(
    private val useCase: GetListTendencyUseCaseImp
    ):ViewModel() {

    private val mainDispatcher = Dispatchers.Main
    private val ioDispatcher = Dispatchers.IO
    private val job = SupervisorJob()
    private val mainDispatcherScope = CoroutineScope(mainDispatcher + job)
    private val ioDispatcherScope = CoroutineScope(ioDispatcher + job)

    var totalPages = MutableLiveData<String>()
    var error = MutableLiveData<String>()
    var list = MutableLiveData<List<MoviesModel>>()

    val moviesList: LiveData<List<MoviesModel>>
        get() = list
    val total: LiveData<String>
        get() = totalPages
    val errorApi:LiveData<String>
        get() = error

    fun init(numPage: String) {
        getList(numPage)
    }

    private fun getList(numPage: String) {
        mainDispatcherScope.launch {
            setList(numPage)
        }
    }

   suspend fun setList(numPage: String) {
        ioDispatcherScope.async {
            return@async useCase.getResponseMoviesTendency(numPage, ::callBackSuccess, ::callBackError)
        }.await()
    }

    private fun callBackSuccess(response: MoviesTendencyResponse?){
        list.postValue(response?.result)
        totalPages.postValue(response?.total_pages)
    }

    private fun callBackError(response: MoviesTendencyResponse?){
        error.postValue(response?.code)
    }
}