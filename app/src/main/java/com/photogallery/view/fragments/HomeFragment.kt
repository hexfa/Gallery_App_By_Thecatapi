package com.photogallery.view.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.photogallery.R
import com.photogallery.adapter.PhotoGalleryAdapter
import com.photogallery.adapter.PhotoGalleryFavoriteAdapter
import com.photogallery.databinding.FragmentHomeBinding
import com.photogallery.viewmodel.ViewModel

class HomeFragment : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private val photoGalleryViewModel: ViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return mBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    /**
     * Used two recycler view in this fragment one for all pictures, one for favorite pictures
     * These two recycler view handled by visible and gone
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.show_favorite -> {
                photoGalleryViewModel.favoriteList.value =
                    !photoGalleryViewModel.favoriteList.value!!
                photoGalleryViewModel.favoriteList.observe(viewLifecycleOwner) {
                    item.setIcon(
                        if (it) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                    )
                }
                if (photoGalleryViewModel.favoriteList.value == true) {
                    val adapter = PhotoGalleryFavoriteAdapter(
                        photoGalleryViewModel.getFavorites(),
                        photoGalleryViewModel
                    )
                    mBinding.recyclerViewPhotoGalleryFavorite.adapter = adapter
                    mBinding.recyclerViewPhotoGalleryFavorite.visibility = View.VISIBLE
                    mBinding.recyclerViewPhotoGallery.visibility = View.GONE
                } else {
                    mBinding.recyclerViewPhotoGalleryFavorite.visibility = View.GONE
                    mBinding.recyclerViewPhotoGallery.visibility = View.VISIBLE
                }
                true
            }
            R.id.refresh -> {
                photoGalleryViewModel.getPhotosRemote(0)
                photoGalleryViewModel.getPhotosRemote(0)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.recyclerViewPhotoGallery.layoutManager = GridLayoutManager(
            context,
            2
        )
        mBinding.recyclerViewPhotoGalleryFavorite.layoutManager = GridLayoutManager(
            context,
            2
        )

        photoGalleryViewModel.photoGalleryItemLivedata.observe(viewLifecycleOwner) {
            val adapter = PhotoGalleryAdapter()
            adapter.addAll(it)
            mBinding.recyclerViewPhotoGallery.adapter = adapter
        }
    }
}