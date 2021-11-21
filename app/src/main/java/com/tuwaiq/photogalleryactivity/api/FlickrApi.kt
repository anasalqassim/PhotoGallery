package com.tuwaiq.photogalleryactivity.api

import com.tuwaiq.photogalleryactivity.models.FlickerResponse
import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {

    @GET("services/rest/?method=flickr.interestingness.getList" +
            "&api_key=d7b49d1d2b40309f1f332d3626aa9e9f" +
            "&extras=url_s" +
            "&format=json" +
            "&nojsoncallback=1")

    fun fetchPhotos(): Call<FlickerResponse>

}