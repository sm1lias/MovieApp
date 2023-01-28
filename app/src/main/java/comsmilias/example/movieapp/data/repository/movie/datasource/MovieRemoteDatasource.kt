package comsmilias.example.movieapp.data.repository.movie.datasource

import comsmilias.example.movieapp.common.Resource
import comsmilias.example.movieapp.domain.model.Movie

interface MovieRemoteDatasource {
    suspend fun getMovies(): Resource<List<Movie>>
}