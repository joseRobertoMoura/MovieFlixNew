package com.example.moviewflixnew.data.model

import android.os.Parcelable
import com.example.moviewflixnew.domain.model.MoviesTendencyUseCase
import com.example.moviewflixnew.ui.model.MoviesModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesTendencyResponse(
    @SerializedName("results")
    var result: List<MoviesModel>?,
    @SerializedName("total_pages")
    var total_pages: String?,
    var code: String?
): Parcelable

fun MoviesTendencyResponse.toMoviesModel() = MoviesTendencyUseCase(
    this.result,
    this.total_pages,
    this.code
)