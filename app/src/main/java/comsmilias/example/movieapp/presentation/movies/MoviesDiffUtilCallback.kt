package comsmilias.example.movieapp.presentation.movies

import androidx.recyclerview.widget.DiffUtil
import comsmilias.example.movieapp.domain.model.Movie

class MoviesDiffUtilCallback(private val oldList: List<Movie>, private val newList: List<Movie>) :
    DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}