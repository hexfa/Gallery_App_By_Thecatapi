package com.photogallery.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.photogallery.R
import com.photogallery.model.local.PhotoListItem
import com.photogallery.databinding.ListItemLoadingBinding
import com.photogallery.databinding.ListItemPhotoGalleryBinding
import com.squareup.picasso.Picasso


class PhotoGalleryAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: MutableList<PhotoListItem> = mutableListOf()
    private val loading = 0
    private val item = 1
    private var isLoadingAdded = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            item -> {
                val binding: ListItemPhotoGalleryBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.list_item_photo_gallery, parent, false
                )
                viewHolder = PhotoGalleryViewHolder(binding)
            }
            loading -> {
                val binding: ListItemLoadingBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.list_item_loading, parent, false
                )
                viewHolder = LoadingVH(binding)
            }
        }

        return viewHolder!!
    }


    fun addAll(data: MutableList<PhotoListItem>) {
        for (photo in data) {
            add(photo)
        }
    }

    private fun add(photo: PhotoListItem) {
        data.add(photo)
        notifyItemInserted(data.size - 1)
    }

    inner class PhotoGalleryViewHolder(private var mBinding: ListItemPhotoGalleryBinding) :
        RecyclerView.ViewHolder(
            mBinding.root
        ) {
        fun bind(photo: PhotoListItem) {
            mBinding.root.setOnClickListener {
                val bundle = bundleOf("id" to photo.id)
                mBinding.root.findNavController()
                    .navigate(R.id.action_homeFragment_to_detailFragment2, bundle)
            }

            Picasso.get()
                .load(photo.url)
                .placeholder(R.mipmap.ic_place_holder)
                .into(mBinding.itemImageView)
        }
    }


    class LoadingVH(private var binding: ListItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.loadmoreProgress.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == data.size - 1 && isLoadingAdded) loading else item
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(PhotoListItem(mutableListOf(), 0, "", "", 0))
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = data.size - 1
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photo = data[position]
        when (getItemViewType(position)) {
            item -> {
                val photoViewHolder: PhotoGalleryViewHolder = holder as PhotoGalleryViewHolder
                photo.let { photoViewHolder.bind(it) }
            }
            loading -> {
                val loadingVH: LoadingVH = holder as LoadingVH
                loadingVH.bind()
            }

        }

    }

}