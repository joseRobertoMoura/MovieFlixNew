package com.example.moviewflixnew.ui.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.data.model.DetailResponse
import com.example.moviewflixnew.usecase.details.GetDetailUseCaseImp
import com.example.moviewflixnew.ui.model.MoviesModel
import javax.inject.Inject

class DetailMovieViewModel @Inject constructor(private var useCase: GetDetailUseCaseImp):ViewModel() {

    private var detailObject = MutableLiveData<DetailResponse>()
    private var error = MutableLiveData<String>()
    val moviesDetail: LiveData<DetailResponse>
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

    private fun callBackSuccess(responseSucess: DetailResponse){
        detailObject.postValue(responseSucess)
    }

    private fun callBackError(responseError: String){
        error.postValue(responseError)
    }
}