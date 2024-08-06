package com.test.pexels.data.model.photo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
open class PhotoSource (
    @PrimaryKey(autoGenerate = true) val scrId : Int? = null,
    @SerializedName("original") val original : String,
    @SerializedName("large2x") val large2x : String,
    @SerializedName("large") val large : String,
    @SerializedName("medium") val medium : String,
    @SerializedName("small") val small : String,
    @SerializedName("portrait") val portrait : String,
    @SerializedName("landscape") val landscape : String,
    @SerializedName("tiny") val tiny : String
) : Parcelable