package comsmilias.example.movieapp.data.repository.movie.datasourceImpl

import comsmilias.example.movieapp.data.db.MovieDao
import comsmilias.example.movieapp.data.repository.movie.datasource.MovieLocalDataSource
import comsmilias.example.movieapp.domain.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
): MovieLocalDataSource {
    override suspend fun getMoviesFromDb(): List<Movie> {
        return movieDao.getMovies()
    }

    override suspend fun getMovieFromDb(id: Int): Movie {
        return movieDao.getMovie(id)
    }


    override suspend fun saveMoviesToDb(movies: List<Movie>) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.saveMovies(movies)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.deleteAllMovies()
        }
    }
}