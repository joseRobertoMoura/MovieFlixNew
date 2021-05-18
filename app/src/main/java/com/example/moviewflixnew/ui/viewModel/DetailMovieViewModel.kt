package com.example.moviewflixnew.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.domain.usecase.GetDetailUseCaseImp
import com.example.moviewflixnew.ui.model.DetailModel
import com.example.moviewflixnew.ui.model.MoviesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailMovieViewModel(private var useCase: GetDetailUseCaseImp):ViewModel() {

    private var detailObject = MutableLiveData<DetailModel>()
    private var error = MutableLiveData<String>()
    val moviesDetail: LiveData<DetailModel>
        get() = detailObject
    val errorApi:LiveData<String>
        get() = error

    fun init(movie: MoviesModel?, context: Context) {
        val id: String? = movie?.id
        setObject(id)
    }

    private fun setObject(id: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                val responseDetail:DetailModel = useCase.getDetail(id)
                if (responseDetail.code.isNullOrEmpty()){
                    detailObject.postValue(responseDetail)
                }else{
                    error.postValue(responseDetail.code)
                }
            }
        }
    }
}