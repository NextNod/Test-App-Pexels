package com.test.pexels.data.model.photo

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
open class Photo(
    @SerializedName("id") @PrimaryKey val id : Int,
    @SerializedName("width") val width : Int,
    @SerializedName("height") val height : Int,
    @SerializedName("url") val url : String?  = null,
    @SerializedName("photographer") val photographer : String,
    @SerializedName("photographer_url") val photographerUrl : String? = null,
    @SerializedName("photographer_id") val photographerId : Int? = null,
    @SerializedName("avg_color") val avgColor : String? = null,
    @SerializedName("src") @Embedded val src : PhotoSource,
    @SerializedName("liked") val liked : Boolean? = null,
    @SerializedName("alt") val alt : String? = null
) : Parcelable