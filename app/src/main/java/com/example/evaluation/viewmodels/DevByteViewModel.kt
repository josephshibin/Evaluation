

package com.example.evaluation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.evaluation.database.VideosDatabase.Companion.getDatabase
import com.example.evaluation.repository.VideosRepository

import kotlinx.coroutines.*
import java.io.IOException


class DevByteViewModel(application: Application) : AndroidViewModel(application) {



    private val videosRepository = VideosRepository(getDatabase(application))


    val playlist = videosRepository.videos


    private var _eventNetworkError = MutableLiveData<Boolean>(false)


//    val eventNetworkError: LiveData<Boolean>
//        get() = _eventNetworkError


    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)


//    val isNetworkErrorShown: LiveData<Boolean>
//        get() = _isNetworkErrorShown


    init {
        refreshDataFromRepository()
    }


    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                videosRepository.refreshVideos()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                if(playlist.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DevByteViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
