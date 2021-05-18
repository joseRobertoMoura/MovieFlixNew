package com.example.moviewflixnew.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.domain.usecase.GetListTendencyUseCase
import com.example.moviewflixnew.domain.usecase.GetListTendencyUseCaseImp
import com.example.moviewflixnew.ui.model.MoviesModel
import com.example.moviewflixnew.ui.model.MoviesTendencyModel
import kotlinx.coroutines.*

class ListMoviesViewModel(private val useCase: GetListTendencyUseCaseImp):ViewModel() {
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
        CoroutineScope(Dispatchers.Main).launch{
            withContext(Dispatchers.IO){
                val response:MoviesTendencyModel = useCase.getResponseMoviesTendency(numPage)
                if (!response.result.isNullOrEmpty()){
                    list.postValue(response.result)
                    totalPages.postValue(response.total_pages)
                }else if(response.result.isNullOrEmpty()){
                    error.postValue(response.code)
                }
            }
        }
    }
}