package comsmilias.example.movieapp.data.repository.movie.datasource

import comsmilias.example.movieapp.common.Resource
import comsmilias.example.movieapp.domain.model.Movie

interface MoviesCacheDataSource {
    suspend fun getMoviesFromCache(): List<Movie>
    suspend fun saveMoviesToCache(movies: List<Movie>)
}