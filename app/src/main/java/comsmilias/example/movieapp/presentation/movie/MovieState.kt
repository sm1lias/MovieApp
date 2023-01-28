package comsmilias.example.movieapp.presentation.movie

import comsmilias.example.movieapp.domain.model.Movie

data class MovieState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: String = ""
)
