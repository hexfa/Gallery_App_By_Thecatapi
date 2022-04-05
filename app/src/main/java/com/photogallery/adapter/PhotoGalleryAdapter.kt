package com.photogallery.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.photogallery.R
import com.photogallery.data.model.PhotoListItem
import com.photogallery.databinding.ListItemPhotoGalleryBinding
import com.squareup.picasso.Picasso

class PhotoGalleryAdapter :
    RecyclerView.Adapter<PhotoGalleryAdapter.ViewHolder>() {
    private var data: MutableList<PhotoListItem> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding: ListItemPhotoGalleryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_photo_gallery, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = data[position]
        photo.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addAll(photos: MutableList<PhotoListItem>) {
        for (photo in photos) {
            add(photo)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun add(photo: PhotoListItem) {
        data.add(photo)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var mBinding: ListItemPhotoGalleryBinding) :
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

}