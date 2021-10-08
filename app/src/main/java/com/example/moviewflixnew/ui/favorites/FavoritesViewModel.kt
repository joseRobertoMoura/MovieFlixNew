package com.example.moviewflixnew.ui.favorites

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviewflixnew.data.model.MoviesModel
import com.example.moviewflixnew.usecase.favorites.FavoritesUseCaseImpl
import kotlinx.coroutines.*
import javax.inject.Inject

sealed class FavoritesPostActionView{
    class FavoriteSuccess(val sucess:String) : FavoritesPostActionView()
    class FavoriteError(val error:String):FavoritesPostActionView()
}

sealed class FavoritListActionView{
    class FavoriteListSuccess(val success: MutableList<MoviesModel?>) : FavoritListActionView()
    class FavoriteListError(val erro: String) : FavoritListActionView()
}

sealed class DeleteMovieActionView{
    class DeleteMovieSuccess(val success: String) : DeleteMovieActionView()
    class DeleteMovieError(val erro: String) : DeleteMovieActionView()
}

class FavoritesViewModel @Inject constructor(
    private val useCase: FavoritesUseCaseImpl,
) : ViewModel() {
    private val mainDispatcher = Dispatchers.Main
    private val ioDispatcher = Dispatchers.IO
    private val job = SupervisorJob()
    private val mainDispatcherScope = CoroutineScope(mainDispatcher + job)
    private val ioDispatcherScope = CoroutineScope(ioDispatcher + job)

    private val _favoriteViewAction by lazy { MutableLiveData<FavoritesPostActionView>() }
    val favoritesViewAction: LiveData<FavoritesPostActionView>
        get() = _favoriteViewAction

    private val _favoritListViewAction by lazy { MutableLiveData<FavoritListActionView>() }
    val favoritListViewAction: LiveData<FavoritListActionView>
        get() = _favoritListViewAction

    private val _deleteFavoriteViewAction by lazy { MutableLiveData<DeleteMovieActionView>() }
    val deleteFavoriteViewAction: LiveData<DeleteMovieActionView>
        get() = _deleteFavoriteViewAction

    fun init(movie: MoviesModel, context: Context) {
        executPostMovieDB(movie, context)
    }

    fun initList(context: Context){
        executeListMovieDB(context)
    }

    fun initDelete(movie: MoviesModel, context: Context){
        executeDelte(movie,context)
    }

    private fun executeDelte(movie: MoviesModel, context: Context) {
        mainDispatcherScope.launch {
            deleteMovieDB(movie,context)
        }
    }


    private fun executeListMovieDB(context: Context) {
        mainDispatcherScope.launch {
            getMovieDB(context)
        }
    }

    private fun executPostMovieDB(movie: MoviesModel, context: Context) {
        mainDispatcherScope.launch {
            PostMovieDB(movie,context)
        }
    }

    private suspend fun PostMovieDB(movie: MoviesModel, context: Context) {
        ioDispatcherScope.async {
            return@async useCase.favoritesUseCase(::success, ::error, movie, context)
        }.await()
    }

    private suspend fun getMovieDB(context: Context) {
        ioDispatcherScope.async {
            return@async useCase.favoritesListUseCase(::successList, ::errorList, context)
        }.await()
    }

    private suspend fun deleteMovieDB(movie: MoviesModel, context: Context) {
        ioDispatcherScope.async {
            return@async useCase.favoritesDeleteUseCase(::successDelete, ::errorDelete, movie, context)
        }.await()
    }

    private fun success(sucess:String){
        _favoriteViewAction.postValue(FavoritesPostActionView.FavoriteSuccess(sucess))
        GlobalScope.coroutineContext.cancelChildren()
    }

    private fun error(error: String){
        _favoriteViewAction.postValue( FavoritesPostActionView.FavoriteError(error))
        GlobalScope.coroutineContext.cancelChildren()
    }

    private fun successList(sucess:MutableList<MoviesModel?>){
        _favoritListViewAction.postValue(FavoritListActionView.FavoriteListSuccess(sucess))
        GlobalScope.coroutineContext.cancelChildren()
    }

    private fun errorList(error: String){
        _favoritListViewAction.postValue( FavoritListActionView.FavoriteListError(error))
        GlobalScope.coroutineContext.cancelChildren()
    }

    private fun successDelete(sucess:String){
        _deleteFavoriteViewAction.postValue(DeleteMovieActionView.DeleteMovieSuccess(sucess))
        GlobalScope.coroutineContext.cancelChildren()
    }

    private fun errorDelete(error: String){
        _deleteFavoriteViewAction.postValue( DeleteMovieActionView.DeleteMovieError(error))
        GlobalScope.coroutineContext.cancelChildren()
    }
}