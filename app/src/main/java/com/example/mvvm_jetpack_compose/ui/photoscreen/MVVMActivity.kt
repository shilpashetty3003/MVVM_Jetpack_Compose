package com.example.mvvm_jetpack_compose.ui.photoscreen

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mvvm_jetpack_compose.data.model.Photo
import com.example.mvvm_jetpack_compose.ui.theme.MVVM_Jetpack_ComposeTheme
import com.example.mvvm_jetpack_compose.utiils.ResponceState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MVVMActivity : ComponentActivity() {

    //    private val viewModel:MVVMViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MVVM_Jetpack_ComposeTheme {
                Surface {
                    MainScreen()
                }
            }
        }
    }
}


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    photoViewModel: MVVMViewModel = hiltViewModel()
) {


    val photos  = photoViewModel.photos.collectAsState()

    var loading by remember {
        mutableStateOf(true)
    }

    when (photos.value) {
        is ResponceState.Failure -> {

            LoadingIndicator(false)
            ShowError((photos.value as ResponceState.Failure).error)

        }

        ResponceState.Loading -> {

            LoadingIndicator(true)
        }

        is ResponceState.Success -> {

            LoadingIndicator(false)
            ShowImages(
                photos = (photos.value as ResponceState.Success<List<Photo>>)
                    .data
            )
        }
    }

}


@Composable
fun ShowImages(photos: List<Photo>) {


    Log.d("TAG", "ShowImages:  $photos")

    LazyColumn {

        items(photos) {
            PhotoCard(photo = it)
        }
    }

}


@Composable
fun ShowError(error: String) {
    Text(text = "Errror $error", style = MaterialTheme.typography.displayLarge)
}


@Composable
fun LoadingIndicator(isLoading: Boolean) {
    if (!isLoading) return
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
//        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )

}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoCard(photo: Photo) {
    Column(modifier = Modifier.padding(15.dp)) {
        Text(text = photo.title, modifier = Modifier, style = MaterialTheme.typography.titleMedium)
        GlideImage(model = photo.url, contentDescription = "", modifier = Modifier.size(50.dp))
    }
}