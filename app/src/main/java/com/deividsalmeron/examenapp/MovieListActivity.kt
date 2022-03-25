package com.deividsalmeron.examenapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deividsalmeron.examenapp.databinding.ActivityMainBinding
import com.deividsalmeron.examenapp.models.Movie
import com.deividsalmeron.examenapp.request.MoviesRepository
import com.deividsalmeron.examenapp.utils.OnClickListener


class MovieListActivity : AppCompatActivity(),OnClickListener {

    private lateinit var mBinding: ActivityMainBinding


    private lateinit var mMoviesAdapter: MoviesAdapter
    private lateinit var mMoviesAdapterLatest:  MoviesAdapter
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private lateinit var mLinearLayoutManagerLatest: LinearLayoutManager
    private var popularMoviesPage = 1
    private var latesMoviesPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        val view= mBinding.root
        setContentView(view)

        mLinearLayoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)
        mLinearLayoutManagerLatest = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)

        mBinding.rvMoviesPopular.layoutManager = mLinearLayoutManager
        mBinding.rvMoviesLatest.layoutManager = mLinearLayoutManagerLatest
        mMoviesAdapter = MoviesAdapter(mutableListOf(),this)
        mMoviesAdapterLatest = MoviesAdapter(mutableListOf(),this)
        mBinding.rvMoviesPopular.adapter = mMoviesAdapter
        mBinding.rvMoviesLatest.adapter = mMoviesAdapterLatest

        getPopularMovies()
        getLatestMovies()
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        mMoviesAdapter = MoviesAdapter(mutableListOf(),this)
        mLinearLayoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)

        mMoviesAdapterLatest = MoviesAdapter(mutableListOf(),this)
        mLinearLayoutManagerLatest = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,
            false)

        mBinding.rvMoviesPopular.apply {
            setHasFixedSize(true)
            layoutManager = mLinearLayoutManager
            adapter = mMoviesAdapter
        }
        mBinding.rvMoviesLatest.apply {
            setHasFixedSize(true)
            layoutManager = mLinearLayoutManagerLatest
            adapter = mMoviesAdapterLatest
        }
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        mMoviesAdapter.appenedMovies(movies)
        attachPopularMoviesOnScrollListener()
    }

    private fun onLatesMoviesFetched(movies: List<Movie>) {
        mMoviesAdapterLatest.appenedMovies(movies)
        attachLatestMoviesOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun getPopularMovies() {
        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }
    private fun getLatestMovies() {
        MoviesRepository.getLatesMovies(
            latesMoviesPage,
            ::onLatesMoviesFetched,
            ::onError
        )
    }

    private fun attachPopularMoviesOnScrollListener() {
        mBinding.rvMoviesPopular.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = mLinearLayoutManager.itemCount
                val visibleItemCount = mLinearLayoutManager.childCount
                val firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    mBinding.rvMoviesPopular.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }

    /*
  * OnClickListener
   */
    override fun onClick(movieId: Long) {
        val args = Bundle()
        args.putLong(getString(R.string.arg_id),movieId)
        Log.i("ID MOVIE:", movieId.toString());
        launchDetailsMovieFragment(args)
    }

    private fun launchDetailsMovieFragment(args: Bundle) {
        val fragment = MovieDetailsFragment()

        if (args != null) fragment.arguments = args

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.containerMain, MovieDetailsFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun attachLatestMoviesOnScrollListener() {
        mBinding.rvMoviesLatest.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = mLinearLayoutManagerLatest.itemCount
                val visibleItemCount = mLinearLayoutManagerLatest.childCount
                val firstVisibleItem = mLinearLayoutManagerLatest.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    mBinding.rvMoviesLatest.removeOnScrollListener(this)
                    latesMoviesPage++
                    getLatestMovies()
                }
            }
        })
    }
}
