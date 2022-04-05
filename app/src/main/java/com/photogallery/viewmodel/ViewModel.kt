package com.photogallery.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.photogallery.data.local.PhotoEntity
import com.photogallery.data.model.PhotoList
import com.photogallery.data.repository.PhotoGalleryRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repo: PhotoGalleryRepositoryImp,
    application: Application,
) : PhotoGalleryViewModel(application) {
    val photoGalleryItemLivedata = MutableLiveData<PhotoList>()
    val favoriteList = MutableLiveData(false)
    private val readOfCash = MutableLiveData(false)

    init {
        readOfCash.value = false
        getPhotosRemote(0)
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
                    photoGalleryItemLivedata.value = t
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