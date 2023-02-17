package com.volokhinaleksey.materialdesign.model

import com.google.gson.annotations.Expose

data class MarsPhotosDTO(
    @Expose
    val photos: List<PhotosDTO>?
)

data class PhotosDTO(
    @Expose
    val id: Long?,
    @Expose
    val sol: Long?,
    @Expose
    val imgSrc: String?,
    @Expose
    val earthDate: String?,
    @Expose
    val camera: CameraDTO?
)

data class CameraDTO(
    @Expose
    val id: Long?,
    @Expose
    val name: String?,
    @Expose
    val roverId: Long?,
    @Expose
    val fullName: String?
)
