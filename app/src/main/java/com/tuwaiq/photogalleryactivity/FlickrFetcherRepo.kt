package com.tuwaiq.photogalleryactivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tuwaiq.photogalleryactivity.api.FlickrApi
import com.tuwaiq.photogalleryactivity.models.FlickerResponse
import com.tuwaiq.photogalleryactivity.models.GalleryItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "FlickrFetcherRepo"
class FlickrFetcherRepo {

    private val flickrApi:FlickrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)

    }

  suspend fun fetchPhotos():List<GalleryItem> {

       // val liveDataResponse:MutableLiveData<List<GalleryItem>> = MutableLiveData()

        val flickrResponse:Call<FlickerResponse> = flickrApi.fetchPhotos()


        val photoResponse = flickrResponse.awaitResponse()

        var galleryItems:List<GalleryItem>? = listOf<GalleryItem>()
      if (photoResponse.isSuccessful) {


          val photo = photoResponse.body()?.photos

          galleryItems = photo?.galleryItems?.filterNot { galleryItem ->
              galleryItem.url.isBlank()
          }

      }else{
          Log.e(TAG, "ohhhh ${photoResponse.errorBody()}")
      }
     // liveDataResponse.value = galleryItems

//        flickrResponse.enqueue(object : Callback<FlickerResponse>{
//            override fun onResponse(
//                call: Call<FlickerResponse>,
//                response: Response<FlickerResponse>
//            ) {
//                Log.d(TAG , " yahhhhh")
//                val flickerResponse:FlickerResponse? = response.body()
//                val photoResponse = flickerResponse?.photos
//                var galleryItems:List<GalleryItem> = photoResponse?.galleryItems ?: mutableListOf()
//
//                galleryItems = galleryItems.filterNot { galleryItem ->
//                galleryItem.url.isBlank()
//                }
//
//                liveDataResponse.value = galleryItems
//            }
//
//            override fun onFailure(call: Call<FlickerResponse>, t: Throwable) {
//                Log.e(TAG , "omg something gone " ,t)
//            }
//
//
//        })


        return galleryItems!!

    }




}