package com.photogallery.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.photogallery.data.local.PhotoEntity
import com.photogallery.data.model.PhotoListItem
import com.photogallery.data.repository.PhotoGalleryRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val
    repo: PhotoGalleryRepositoryImp,
    application: Application,
) : PhotoGalleryViewModel(application) {
    private var photoId: String? = null

    fun savePhotoId(id: String?) {
        photoId = id
        getPhotoDetail()
    }

    val photoDetail = MutableLiveData<PhotoListItem>()
    val isFavorite = MutableLiveData<Boolean>()
    private fun getPhotoDetail() {
        photoId?.let {
            repo.getPhotoDetail(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<PhotoListItem> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onSuccess(t: PhotoListItem) {
                        photoDetail.value = t
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        }
    }

    fun updateFavoriteStatus(photoEntity: PhotoEntity) {
        if (isFavorite.value == true) {
            repo.deletePhoto(photoEntity)
        } else {
            repo.addPhoto(photoEntity)
        }
        isFavorite.value?.let {
            isFavorite.value = !it
        }
    }

    /**
     * This method is used to check favorite status
     */
    fun checkFavoriteStatus(photoId: String) {
        isFavorite.value = repo.isFavorite(photoId)
    }
}