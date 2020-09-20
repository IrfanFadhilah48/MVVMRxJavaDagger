package com.irfan.mvvmrxjavadagger.ui.main.model
import com.google.gson.annotations.SerializedName


data class GenreResponse(
    @SerializedName("genres")
    var genres: List<Genre>?
)

data class Genre(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?
)