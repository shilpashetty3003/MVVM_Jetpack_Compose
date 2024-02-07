package com.example.mvvm_jetpack_compose.ui.photoscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_jetpack_compose.data.model.Photo
import com.example.mvvm_jetpack_compose.data.repository.PhotoRepository
import com.example.mvvm_jetpack_compose.utiils.ResponceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class MVVMViewModel @Inject constructor(private val repository: PhotoRepository) : ViewModel() {

    private val _photos = MutableStateFlow<ResponceState<List<Photo>>>(ResponceState.Loading)

    val photos: StateFlow<ResponceState<List<Photo>>>
        get() = _photos


    init {
        getPhotos()
    }

    private fun getPhotos() {
        viewModelScope.launch {
            repository.getPhotosRepo().catch { it ->
                _photos.value = ResponceState.Failure(it.localizedMessage)
            }.collect {
                _photos.value = ResponceState.Success(it)

                Log.d("TAG", "getPhotos:  $it")
            }
        }

    }


}