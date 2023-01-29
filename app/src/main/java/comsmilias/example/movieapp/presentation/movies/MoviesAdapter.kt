package comsmilias.example.movieapp.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import comsmilias.example.movieapp.databinding.MovieItemBinding
import comsmilias.example.movieapp.domain.model.Movie

class MoviesAdapter(private val onItemClick: (Int) -> Unit): RecyclerView.Adapter<MyViewHolder>() {
    private val moviesList = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return MyViewHolder(binding){
            onItemClick(it)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount() = moviesList.size

    fun setMovieList(newData: List<Movie>) {
        val diffCallback = MoviesDiffUtilCallback(moviesList, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        moviesList.clear()
        moviesList.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

}

class MyViewHolder(
    private val binding: MovieItemBinding,
    onItemClicked: (Int) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }
    fun bind(movie: Movie){
        binding.apply {
            imgMovie.load(movie.imageUrlShort)
            txtName.text = movie.name
            txtRating.text = movie.rating.toString()
        }
    }

}