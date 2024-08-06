package com.test.pexels.data.model

import com.google.gson.annotations.SerializedName

data class BasePaginationPage<T> (
    @SerializedName(
        "photos",
        alternate = [ "collections", "media" ]
    ) val values : List<T> = listOf(),

    @SerializedName("page") val page : Int? = null,
    @SerializedName("per_page") val perPage : Int? = null,
    @SerializedName("total_results") val totalResults : Int? = null,
    @SerializedName("next_page") val nextPage : String? = null,
    @SerializedName("prev_page") val prevPage : String? = null
)