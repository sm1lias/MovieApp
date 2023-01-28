package comsmilias.example.movieapp.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comsmilias.example.movieapp.common.Resource
import comsmilias.example.movieapp.domain.usecase.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieFragmentViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MovieState>(MovieState())
    val state: StateFlow<MovieState> = _state


    fun getMovie(id: Int) {
        viewModelScope.launch {
            getMovieUseCase(id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = MovieState(movie = result.data)
                    }
                    is Resource.Loading -> {
                        _state.value = MovieState(isLoading = true)
                    }
                    is Resource.Error ->
                        _state.value = MovieState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                }
            }
        }
    }


}