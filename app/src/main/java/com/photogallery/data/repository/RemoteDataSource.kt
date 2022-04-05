package com.photogallery.data.repository

import com.photogallery.data.model.PhotoList
import com.photogallery.data.model.PhotoListItem
import com.photogallery.data.remote.ApiService
import io.reactivex.Single
import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    fun getPhotos(page: Int): Single<PhotoList> = apiService.getPhotos(page)
    fun getPhotoDetail(id: String): Single<PhotoListItem> = apiService.getPhotoDetail(id)
}