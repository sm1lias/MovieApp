package comsmilias.example.movieapp.domain.model.repository

import comsmilias.example.movieapp.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(refresh: Boolean): List<Movie>

    suspend fun getMovie(id: Int): Movie
}