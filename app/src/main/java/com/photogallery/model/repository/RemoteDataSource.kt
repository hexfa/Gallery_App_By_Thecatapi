package com.photogallery.model.repository

import com.photogallery.model.local.PhotoList
import com.photogallery.model.local.PhotoListItem
import com.photogallery.model.remote.ApiService
import io.reactivex.Single
import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    fun getPhotos(page: Int): Single<PhotoList> = apiService.getPhotos(page)
    fun getPhotoDetail(id: String): Single<PhotoListItem> = apiService.getPhotoDetail(id)
}