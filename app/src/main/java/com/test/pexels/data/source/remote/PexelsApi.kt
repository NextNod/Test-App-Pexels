package com.test.pexels.data.source.remote

import com.test.pexels.data.model.BasePaginationPage
import com.test.pexels.domain.models.collection.FeaturedCollection
import com.test.pexels.domain.models.photo.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApi {
    @GET("collections/featured")
    fun getFeaturedCollections(
        @Query("per_page") size : Int = 7
    ) : Call<BasePaginationPage<FeaturedCollection>>

    @GET("curated")
    fun getDefaultPhotos(
        @Query("page") page : Int = 0,
        @Query("per_page") size : Int = 30
    ) : Call<BasePaginationPage<Photo>>

    @GET("search")
    fun searchPhoto(
        @Query("query") query : String,
        @Query("page") page : Int = 0,
        @Query("per_page") size : Int = 30
    ) : Call<BasePaginationPage<Photo>>

    @GET("collections/{id}")
    fun getPhotosByCollection(
        @Path("id") id : String,
        @Query("page") page : Int = 0,
        @Query("per_page") size : Int = 30
    ) : Call<BasePaginationPage<Photo>>
}