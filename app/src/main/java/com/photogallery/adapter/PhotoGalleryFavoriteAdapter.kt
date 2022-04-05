package com.photogallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.photogallery.R
import com.photogallery.data.local.PhotoEntity
import com.photogallery.databinding.ListItemPhotoGalleryBinding
import com.photogallery.viewmodel.ViewModel

class PhotoGalleryFavoriteAdapter(
    private val data: MutableList<PhotoEntity>?,
    private val viewModel: ViewModel,
) :
    RecyclerView.Adapter<PhotoGalleryFavoriteAdapter.ViewHolder>() {
    var set: Set<String> = HashSet()

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
        val photo = data?.get(position)
        photo?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    inner class ViewHolder(private var mBinding: ListItemPhotoGalleryBinding) :
        RecyclerView.ViewHolder(
            mBinding.root
        ) {
        fun bind(photo: PhotoEntity) {
            mBinding.root.setOnClickListener {
                val bundle = bundleOf("id" to photo.id)
                mBinding.root.findNavController()
                    .navigate(R.id.action_homeFragment_to_detailFragment2, bundle)
            }
            mBinding.itemImageView.setImageBitmap(viewModel.stringToBitMap(photo.bitmap))
        }
    }

}