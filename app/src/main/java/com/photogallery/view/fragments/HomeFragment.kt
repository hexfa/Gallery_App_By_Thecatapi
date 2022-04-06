package com.photogallery.view.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.photogallery.R
import com.photogallery.view.adapter.PhotoGalleryAdapter
import com.photogallery.view.adapter.PhotoGalleryFavoriteAdapter
import com.photogallery.databinding.FragmentHomeBinding
import com.photogallery.view.util.PaginationScrollListener
import com.photogallery.viewmodel.ViewModel


class HomeFragment : Fragment() {
    private val pageStart: Int = 1
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var currentPage: Int = pageStart
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
                        photoGalleryViewModel.getFavorites()
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        mBinding.recyclerViewPhotoGallery.layoutManager = linearLayoutManager
        val adapter = PhotoGalleryAdapter()
        mBinding.recyclerViewPhotoGallery.adapter = adapter
        mBinding.recyclerViewPhotoGalleryFavorite.layoutManager = GridLayoutManager(
            context,
            2
        )

        photoGalleryViewModel.photoGalleryItemLivedataFirstPage.observe(viewLifecycleOwner) {
            mBinding.progressbar.visibility = View.GONE
            adapter.addAll(it)
            adapter.addLoadingFooter()
        }
        photoGalleryViewModel.photoGalleryItemLivedataNextPage.observe(viewLifecycleOwner) {
            adapter.removeLoadingFooter()
            isLoading = false
            adapter.addAll(it)
            adapter.addLoadingFooter()
        }
        mBinding.recyclerViewPhotoGallery.addOnScrollListener(object :
            PaginationScrollListener(mBinding.recyclerViewPhotoGallery.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                loadNextPage()
            }


            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })
    }

    private fun loadNextPage() {
        photoGalleryViewModel.getPhotosRemote(currentPage)
    }
}