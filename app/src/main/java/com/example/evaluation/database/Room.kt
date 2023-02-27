

package com.example.evaluation.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VideoDao {
    @Query("select * from databasevideo")
    fun getVideos(): LiveData<List<DatabaseVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<DatabaseVideo>)
}



@Database(entities = [DatabaseVideo::class], version = 1)
abstract class VideosDatabase: RoomDatabase() {
    abstract val videoDao: VideoDao

companion object{
    private  var INSTANCE: VideosDatabase? =null

    fun getDatabase(context: Context): VideosDatabase {
        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                VideosDatabase::class.java, "data"
            )
                // line 30 is for giving data while coding itself this line calls the function below
                //.addCallback(HospitalDatabaseCallback(scope))
                .build()

            INSTANCE = instance

            instance

        }
    }
    }
}
