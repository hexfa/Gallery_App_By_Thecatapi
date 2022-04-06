package com.photogallery.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Photo")
data class PhotoEntity
    (
    @PrimaryKey var id: String,
    var height: Int,
    var url: String,
    var width: Int,
)