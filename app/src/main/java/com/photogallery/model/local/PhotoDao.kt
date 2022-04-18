package com.photogallery.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: PhotoEntity): Completable

    @Query("SELECT * FROM Photo")
    fun loadAll(): Single<MutableList<PhotoEntity>>

    @Query("DELETE FROM Photo where id = :photoId")
    fun delete(photoId: String): Completable

    @Query("SELECT * FROM Photo where id = :photoId")
    fun loadOneByPhotoId(photoId: String): Single<PhotoEntity?>
}
