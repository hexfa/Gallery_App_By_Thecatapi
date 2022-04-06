package com.photogallery.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.photogallery.model.local.PhotoEntity
import com.photogallery.model.local.PhotoList
import com.photogallery.model.repository.PhotoGalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repo: PhotoGalleryRepository,
    application: Application,
) : PhotoGalleryViewModel(application) {
    val photoGalleryItemLivedataFirstPage = MutableLiveData<PhotoList>()
    val photoGalleryItemLivedataNextPage = MutableLiveData<PhotoList>()
    val favoriteList = MutableLiveData(false)

    init {
        repo.getPhoto(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<PhotoList> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: PhotoList) {
                    photoGalleryItemLivedataFirstPage.value = t
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    fun getPhotosRemote(page: Int) {
        repo.getPhoto(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<PhotoList> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: PhotoList) {
                    photoGalleryItemLivedataNextPage.value = t
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    fun getFavorites(): MutableList<PhotoEntity> {
        return repo.getAll()
    }

}