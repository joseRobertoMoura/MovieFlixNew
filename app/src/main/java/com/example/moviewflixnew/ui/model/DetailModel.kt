package com.example.moviewflixnew.ui.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetailModel(
    @SerializedName("original_title")
    var original_title: String?,
    @SerializedName("poster_path")
    var poster_path: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("original_name")
    var original_name: String?,
    var code: String?
):Parcelable