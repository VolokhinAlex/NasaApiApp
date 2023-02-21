package com.volokhinaleksey.materialdesign.model

import com.google.gson.annotations.Expose

/**
 * Date class for storing information coming from the Nasa API
 *
 * @param photos - An object for storing information about a photo
 *
 */

data class MarsPhotosDTO(
    @Expose
    val photos: List<PhotoDTO>?
)


/**
 * @param id - unique identifier of each photo
 * @param sol - Martian sol of the Rover's mission
 * @param imgSrc - Link to the photo
 * @param earthDate - Date when the photo was taken
 * @param camera - An object for storing information about a details photo
 */

data class PhotoDTO(
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

/**
 * @param id - unique identifier of each photo
 * @param name - The short name of the camera the photo was taken on
 * @param roverId - unique identifier of rover
 * @param fullName - Full name of the camera the photo was taken on
 */

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
