package com.photogallery.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Photo")
data class PhotoEntity
    (
    @PrimaryKey var id: String,
    var bitmap: String?,
    var title: String,
)