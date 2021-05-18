package com.example.moviewflixnew.domain.model

import android.os.Parcelable
import com.example.moviewflixnew.data.model.toMoviesModel
import com.example.moviewflixnew.ui.model.DetailModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailUseCase(
    @SerializedName("original_title")
    var original_title: String?,
    @SerializedName("poster_path")
    var poster_path: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("original_name")
    var original_name: String?,
    var code: String?
): Parcelable

fun DetailUseCase.toDetailModel() = DetailModel(
    this.original_title,
    this.poster_path,
    this.overview,
    this.original_name,
    this.code
)