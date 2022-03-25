package com.deividsalmeron.examenapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.deividsalmeron.examenapp.databinding.FragmentMovieDetailsBinding
import com.deividsalmeron.examenapp.models.Movie
import com.deividsalmeron.examenapp.request.MoviesRepository


class MovieDetailsFragment : Fragment() {
    private lateinit var mBinding: FragmentMovieDetailsBinding
    private var mActivity:MovieListActivity? = null
    private lateinit var id: String ;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mBinding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = arguments?.getString(R.string.arg_id.toString()).toString()

        Log.i("Id_mov",id)
        getLatestMovies();


        mActivity = activity as? MovieListActivity

        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        mActivity?.supportActionBar?.title = getString(R.string.edit_store_title_add)

        setHasOptionsMenu(true)

//        mBinding.etPhoto.addTextChangedListener {
//            Glide.with(this)
//                .load(mBinding.etPhoto.text.toString())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop()
//                .into(mBinding.imgPhoto)
//        }
    }

    private fun getLatestMovies() {
        MoviesRepository.getDetailMovie(
            id.toLong(),
            ::onLatesMoviesFetched,
            ::onError
        )
    }

    private fun onLatesMoviesFetched() {
    }
    private fun onError() {
    }
}