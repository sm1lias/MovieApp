package comsmilias.example.movieapp.data.repository.movie.datasource

import comsmilias.example.movieapp.domain.model.Movie

interface MovieLocalDataSource {
    suspend fun getMoviesFromDb(): List<Movie>
    suspend fun getMovieFromDb(id: Int): Movie
    suspend fun saveMoviesToDb(movies: List<Movie>)
    suspend fun clearAll()
}