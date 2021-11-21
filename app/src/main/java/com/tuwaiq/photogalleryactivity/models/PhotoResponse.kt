package com.tuwaiq.photogalleryactivity.models

import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("photo")
    lateinit var galleryItems:List<GalleryItem>
}