package comsmilias.example.movieapp.presentation.movies

import comsmilias.example.movieapp.domain.model.Movie

data class MoviesListState(
    val isLoading: Boolean = false,
    val movies: List<Movie>? = null,
    var error: String = ""
)
