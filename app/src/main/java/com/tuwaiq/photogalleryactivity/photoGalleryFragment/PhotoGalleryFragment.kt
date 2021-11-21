package com.tuwaiq.photogalleryactivity.photoGalleryFragment

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Precision
import coil.size.Scale
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.squareup.picasso.Picasso
import com.tuwaiq.photogalleryactivity.FlickrFetcherRepo
import com.tuwaiq.photogalleryactivity.R
import com.tuwaiq.photogalleryactivity.api.FlickrApi
import com.tuwaiq.photogalleryactivity.models.GalleryItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.coroutines.coroutineContext


private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment : Fragment() {

    private lateinit var photoGalleryRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val photoGalleryViewModel by lazy { ViewModelProvider(this)[PhotoGalleryViewModel::class.java] }

    var data = listOf<GalleryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)









    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {

            data =  photoGalleryViewModel.fetchPhotos()



//            photoGalleryViewModel.fetchPhotos().observe(
//                viewLifecycleOwner, Observer { response:List<GalleryItem>->
//
//                    photoGalleryRecyclerView.adapter = GalleryAdapter(response)
//
//                }
//            )

        }.invokeOnCompletion {
            progressBar.visibility = View.GONE
            photoGalleryRecyclerView.adapter  = GalleryAdapter(data)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_photo_gallery, container, false)
        photoGalleryRecyclerView = v.findViewById(R.id.photos_rv)
        progressBar = v.findViewById(R.id.progressBar)

        photoGalleryRecyclerView.apply {
            layoutManager = GridLayoutManager(context,3)
        }

        return v
    }

    private class GalleryHolder( view: View):RecyclerView.ViewHolder(view){

        private var photoImageView: ImageView = itemView.findViewById(R.id.photo_item)


        fun bind(galleryItem:GalleryItem){
            photoImageView.load(galleryItem.url) {
                placeholder(R.drawable.ic_android_black_24dp)
                crossfade(300) // 75th percentile of a second
            }
//            Picasso.get()
//                .load(galleryItem.url)
//                .resize(150,150)
//                .centerCrop()
//                .into(photoImageView)

        }

    }

    private inner class GalleryAdapter(val galleryItems:List<GalleryItem>)
        :RecyclerView.Adapter<GalleryHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryHolder {
            val view = layoutInflater.inflate(
                R.layout.photo_list_item,
                parent,
                false
            )

            return GalleryHolder(view)
        }

        override fun onBindViewHolder(holder: GalleryHolder, position: Int) {
            val galleryItem = galleryItems[position]
            holder.bind(galleryItem)
        }

        override fun getItemCount(): Int = galleryItems.size
    }


}
