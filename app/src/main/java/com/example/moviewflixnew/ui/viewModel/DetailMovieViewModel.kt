package com.example.moviewflixnew.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.domain.model.DetailUseCase
import com.example.moviewflixnew.domain.usecase.GetDetailUseCaseImp
import com.example.moviewflixnew.ui.model.DetailModel
import com.example.moviewflixnew.ui.model.MoviesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailMovieViewModel @Inject constructor(private var useCase: GetDetailUseCaseImp):ViewModel() {

    private var detailObject = MutableLiveData<DetailUseCase>()
    private var error = MutableLiveData<String>()
    val moviesDetail: LiveData<DetailUseCase>
        get() = detailObject
    val errorApi:LiveData<String>
        get() = error

    fun init(movie: MoviesModel?, context: Context) {
        val id: String? = movie?.id
        setObject(id)
    }

    private fun setObject(id: String?) {
        useCase.getDetail(id,::callBackSuccess,::callBackError)
    }

    private fun callBackSuccess(responseSucess: DetailUseCase){
        detailObject.postValue(responseSucess)
    }

    private fun callBackError(responseError: String){
        error.postValue(responseError)
    }
}