package comsmilias.example.movieapp.presentation.movies

import androidx.recyclerview.widget.DiffUtil
import comsmilias.example.movieapp.domain.model.Movie

class MoviesDiffUtilCallback(private val oldList: List<Movie>, private val newList: List<Movie>) :
    DiffUtil.Callback() {

//    override fun getOldListSize(): Int = oldList.size
//    override fun getNewListSize(): Int = newList.size
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
//        oldLi
//    }
//
//    override fun areContentsTheSame(oldMovie: Int, newPosition: Int): Boolean {
//        val (_, value, name) = oldList[oldMovie]
//        val (_, value1, name1) = newList[newPosition]
//        return name == name1 && value == value1
//    }

    //    @Nullable
//    override fun geeksPayload(oldMovie: Int, newPosition: Int): Any? {
//        return super.geeksPayload(oldMovie, newPosition)
//    }
//    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
//        return oldItem == newItem
//    }
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}