package com.photogallery.data.local

import androidx.room.*

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: PhotoEntity): Long

    @Query("SELECT * FROM Photo")
    fun loadAll(): MutableList<PhotoEntity>

    @Query("DELETE FROM Photo where id = :photoId")
    fun delete(photoId: String)

    @Query("DELETE FROM Photo")
    fun deleteAll()

    @Query("SELECT * FROM Photo where id = :photoId")
    fun loadOneByPhotoId(photoId: String): PhotoEntity?

    @Update
    fun update(photo: PhotoEntity)
}
