package com.example.moviewflixnew.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesModel(
    @SerializedName("id")
    var id: String?,
    @SerializedName("original_title")
    var original_title: String?,
    @SerializedName("poster_path")
    var poster_path: String?,
    @SerializedName("original_name")
    var original_name: String?,
):Parcelable
