package com.photogallery.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import java.io.ByteArrayOutputStream

abstract class PhotoGalleryViewModel(application: Application) : AndroidViewModel(application) {
    val compositeDisposable = CompositeDisposable()
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    /**
     * This method is used to convert bitmap to strings
     */
    fun bitMapToString(bitmap: Bitmap?): String? {
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    /**
     * This method is used to convert string to bitmap
     */
    fun stringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }

}