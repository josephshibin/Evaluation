
package com.example.evaluation.repository

import android.util.Log
import androidx.lifecycle.LiveData

import androidx.lifecycle.Transformations

import com.example.evaluation.database.VideosDatabase
import com.example.evaluation.database.asDomainModel
import com.example.evaluation.domain.DevByteVideo
import com.example.evaluation.network.DevByteNetwork

import com.example.evaluation.network.asDatabaseModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class VideosRepository(private val database: VideosDatabase) {

    val videos: LiveData<List<DevByteVideo>> = Transformations.map(database.videoDao.getVideos()) {
        it.asDomainModel()
    }


    //reloading
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = DevByteNetwork.devbytes.getPlaylist()
            Log.i("videos", playlist.toString())
            database.videoDao.insertAll(playlist.asDatabaseModel())
        }
    }
}

