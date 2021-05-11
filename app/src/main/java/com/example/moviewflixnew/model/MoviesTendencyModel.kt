package com.example.moviewflixnew.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class MoviesTendencyModel(
    @SerializedName("results")
    var result: List<MoviesModel>,
    @SerializedName("total_pages")
    var total_pages: String
):Parcelable