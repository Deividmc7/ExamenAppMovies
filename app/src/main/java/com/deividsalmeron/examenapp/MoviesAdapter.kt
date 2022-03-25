package com.deividsalmeron.examenapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.deividsalmeron.examenapp.databinding.ItemMovieBinding
import com.deividsalmeron.examenapp.models.Movie
import com.deividsalmeron.examenapp.utils.OnClickListener

class MoviesAdapter(
    private var movies: MutableList<Movie>,
    private var listener: OnClickListener
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        mContext = parent.context
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie= movies.get(position)
        with(holder){
            setListener(movie)
            binding.itemMovieTitle.setText(movie.title)

            Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(CenterCrop())
                .into(binding.itemMoviePoster)
        }
//        holder.bind(movies[position])


    }

    fun appenedMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding =  ItemMovieBinding.bind(itemView)

        fun setListener(movie: Movie){
            with(binding.root){
                setOnClickListener{ listener.onClick(movie.id)}
            }
        }
    }
}