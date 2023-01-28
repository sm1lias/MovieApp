package comsmilias.example.movieapp.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comsmilias.example.movieapp.domain.model.Movie
import comsmilias.example.movieapp.domain.model.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieFragmentViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieLiveData = MutableLiveData<Movie>()

    val movieLiveData: LiveData<Movie>
        get() = _movieLiveData

    fun getMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movie = repository.getMovie(id)
            withContext(Dispatchers.Main){
                _movieLiveData.value = movie
            }
        }
    }


}