package com.tuwaiq.photogalleryactivity.photoGalleryFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tuwaiq.photogalleryactivity.FlickrFetcherRepo
import com.tuwaiq.photogalleryactivity.models.GalleryItem

class PhotoGalleryViewModel :ViewModel() {

    private val flickrFetcherRepo = FlickrFetcherRepo()


    suspend fun fetchPhotos():List<GalleryItem>{
      return flickrFetcherRepo.fetchPhotos()
  }


}