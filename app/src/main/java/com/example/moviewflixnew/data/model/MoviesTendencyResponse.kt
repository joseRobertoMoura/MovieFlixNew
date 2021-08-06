package com.example.moviewflixnew.data.model

import android.os.Parcelable
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
