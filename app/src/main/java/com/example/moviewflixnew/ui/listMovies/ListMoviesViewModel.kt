package com.example.moviewflixnew.ui.listMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.data.model.MoviesTendencyResponse
import com.example.moviewflixnew.usecase.tendency.GetListTendencyUseCaseImp
import com.example.moviewflixnew.ui.model.MoviesModel
import javax.inject.Inject

class ListMoviesViewModel @Inject constructor(private val useCase: GetListTendencyUseCaseImp):ViewModel() {
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
        setList(numPage)
    }

    fun setList(numPage: String) {
        useCase.getResponseMoviesTendency(numPage, ::callBackSuccess, ::callBackError)
    }

    private fun callBackSuccess(response: MoviesTendencyResponse){
        list.postValue(response.result)
        totalPages.postValue(response.total_pages)
    }

    private fun callBackError(response: MoviesTendencyResponse){
        error.postValue(response.code)
    }
}