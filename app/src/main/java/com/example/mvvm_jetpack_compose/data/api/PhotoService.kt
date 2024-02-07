package com.example.mvvm_jetpack_compose.data.api

import com.example.mvvm_jetpack_compose.data.model.Photo
import retrofit2.http.GET


interface PhotoService {

    @GET("/photos")
    suspend  fun getPhotos():List<Photo>
}