package com.example.moviewflixnew.domain.model

import android.os.Parcelable
import com.example.moviewflixnew.ui.model.MoviesModel
import com.example.moviewflixnew.ui.model.MoviesTendencyModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class MoviesTendencyUseCase(
    @SerializedName("results")
    var result: List<MoviesModel>?,
    @SerializedName("total_pages")
    var total_pages: String?,
    var code:String?
): Parcelable

fun MoviesTendencyUseCase.toMoviesModelUseCase() = MoviesTendencyModel(
    this.result,
    this.total_pages,
    this.code
)