package com.example.moviewflixnew.ui.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.data.model.DetailResponse
import com.example.moviewflixnew.data.model.login.LoginFireBaseModel
import com.example.moviewflixnew.ui.login.LoginActionView
import com.example.moviewflixnew.usecase.details.GetDetailUseCaseImp
import com.example.moviewflixnew.ui.model.MoviesModel
import kotlinx.coroutines.*
import javax.inject.Inject

sealed class DetailsActionView{
    class Success(val responseSucess: DetailResponse?) : DetailsActionView()
    class Error(val error:String?):DetailsActionView()
}


class DetailMovieViewModel @Inject constructor(
    private var useCase: GetDetailUseCaseImp
    ):ViewModel() {

    private val mainDispatcher = Dispatchers.Main
    private val ioDispatcher = Dispatchers.IO
    private val job = SupervisorJob()
    private val mainDispatcherScope = CoroutineScope(mainDispatcher + job)
    private val ioDispatcherScope = CoroutineScope(ioDispatcher + job)

    private val _detailsViewAction by lazy { MutableLiveData<DetailsActionView>() }
    val detailsViewAction: LiveData<DetailsActionView>
        get() = _detailsViewAction

    fun init(movie: MoviesModel?, context: Context) {
        val id: String? = movie?.id
        executeLogin(id)
    }

    private fun executeLogin(id: String?) {
        mainDispatcherScope.launch {
            getDetail(id)
        }
    }

    private suspend fun getDetail(id: String?) {
        ioDispatcherScope.async {
            return@async useCase.getDetail(id,::callBackSuccess,::callBackError)
        }.await()
    }


    private fun callBackSuccess(responseSucess: DetailResponse?){
        _detailsViewAction.postValue(DetailsActionView.Success(responseSucess))
    }

    private fun callBackError(responseError: String?){
        _detailsViewAction.postValue(DetailsActionView.Error(responseError))
    }
}