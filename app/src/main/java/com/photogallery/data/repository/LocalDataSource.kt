package com.photogallery.data.repository


import com.photogallery.data.local.AppDataBase
import com.photogallery.data.local.PhotoEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val database: AppDataBase) {
    fun isFavorite(photoId: String): Boolean {
        return database.photoDao.loadOneByPhotoId(photoId) != null
    }

    fun deletePhoto(photoId: String) {
        database.photoDao.delete(photoId)
    }

    fun addPhoto(photo: PhotoEntity) {
        database.photoDao.insert(photo)
    }

    fun getAll(): MutableList<PhotoEntity> {
        return database.photoDao.loadAll()
    }
}


