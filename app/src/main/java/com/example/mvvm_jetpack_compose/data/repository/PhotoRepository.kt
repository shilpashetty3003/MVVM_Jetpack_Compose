package com.example.mvvm_jetpack_compose.data.repository

import com.example.mvvm_jetpack_compose.data.api.PhotoService
import com.example.mvvm_jetpack_compose.data.model.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val photoService: PhotoService) {


    fun getPhotosRepo(): Flow<List<Photo>> {

        return flow {
            emit(photoService.getPhotos())
        }
    }
}