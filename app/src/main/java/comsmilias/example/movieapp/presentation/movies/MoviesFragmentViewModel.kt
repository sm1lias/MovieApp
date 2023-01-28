package comsmilias.example.movieapp.presentation.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comsmilias.example.movieapp.domain.model.Movie
import comsmilias.example.movieapp.domain.model.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesFragmentViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    var data = MutableLiveData<List<Movie>>()

    init {
        viewModelScope.launch {
            data.value = repository.getMovies(false)
        }
    }

    fun getRefreshMovies(){
        viewModelScope.launch {
            data.value = repository.getMovies(true)
        }
    }
}