package com.example.mvvm_jetpack_compose.utiils

import com.example.mvvm_jetpack_compose.data.model.Photo

sealed class ResponceState<out T> {
    data class Success<T>(val data:T) : ResponceState<T>()
    data class Failure(val error : String) :ResponceState<Nothing>()
    object Loading : ResponceState<Nothing>()
}
