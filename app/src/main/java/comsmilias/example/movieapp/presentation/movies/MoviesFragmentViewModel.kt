package comsmilias.example.movieapp.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comsmilias.example.movieapp.common.Resource
import comsmilias.example.movieapp.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesFragmentViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MoviesListState>(MoviesListState())
    val state: StateFlow<MoviesListState> = _state

    init {
        getMovies()
    }

    fun getMovies(refresh: Boolean = false) {
        viewModelScope.launch {
            getMoviesUseCase(refresh).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = MoviesListState(movies = result.data)
                    }
                    is Resource.Error -> {
                        _state.value = MoviesListState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = MoviesListState(isLoading = true)
                    }
                }
            }
        }
    }
}