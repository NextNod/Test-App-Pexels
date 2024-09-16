package com.test.pexels.domain.models.collection

import com.google.gson.annotations.SerializedName

data class FeaturedCollection (
    @SerializedName("id") val id : String,
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String? = null,
    @SerializedName("private") val private : Boolean? = null,
    @SerializedName("media_count") val mediaCount : Int? = null,
    @SerializedName("photos_count") val photosCount : Int? = null,
    @SerializedName("videos_count") val videosCount : Int? = null
)