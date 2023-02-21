package com.volokhinaleksey.materialdesign.model

import com.google.gson.annotations.Expose

/**
 * Date class for storing information coming from the Nasa API
 *
 *  @param copyright - The author who took the photo of the day
 *  @param date - The date when it was made
 *  @param explanation - Descriptions of the photo of the day
 *  @param mediaType - Media response type
 *  @param title - Title of the photo of the day
 *  @param url - Url of the photo of the day
 *  @param hdurl - Url to the photo of the day in HD quality
 *
 */

data class PictureOfTheDayDTO(
    @Expose
    val copyright: String?,
    @Expose
    val date: String?,
    @Expose
    val explanation: String?,
    @Expose
    val mediaType: String?,
    @Expose
    val title: String?,
    @Expose
    val url: String?,
    @Expose
    val hdurl: String?
)