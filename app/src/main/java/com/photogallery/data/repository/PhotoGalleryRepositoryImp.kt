package com.photogallery.data.repository

import com.photogallery.data.local.PhotoEntity
import com.photogallery.data.model.PhotoList
import com.photogallery.data.model.PhotoListItem
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.Single
import javax.inject.Inject

@ActivityRetainedScoped
class PhotoGalleryRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource,
) {
    fun getPhoto(page: Int): Single<PhotoList> = remoteDataSource.getPhotos(page)
    fun getPhotoDetail(id: String): Single<PhotoListItem> = remoteDataSource.getPhotoDetail(id)
    fun isFavorite(photoId: String): Boolean {
        return localDataSource.isFavorite(photoId)
    }

    fun deletePhoto(photo: PhotoEntity) {
        localDataSource.deletePhoto(photo.id)
    }

    fun addPhoto(photo: PhotoEntity) {
        localDataSource.addPhoto(photo)
    }

    fun getAll(): MutableList<PhotoEntity> {
        return localDataSource.getAll()
    }
}