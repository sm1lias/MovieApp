package comsmilias.example.movieapp.domain.repository

import comsmilias.example.movieapp.common.Resource
import comsmilias.example.movieapp.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(refresh: Boolean): Resource<List<Movie>>

    suspend fun getMovie(id: Int): Resource<Movie>
}