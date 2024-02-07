package com.example.mvvm_jetpack_compose.di

import com.example.mvvm_jetpack_compose.data.api.PhotoService
import com.example.mvvm_jetpack_compose.utiils.CONSTANTS
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {



    @Singleton
    @Provides
    fun provideRetrofitService():Retrofit{
        return Retrofit.Builder().baseUrl(CONSTANTS.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
    }


    @Singleton
    @Provides
    fun providePhotoService(retrofit: Retrofit):PhotoService{
        return retrofit.create(PhotoService::class.java);
    }
}