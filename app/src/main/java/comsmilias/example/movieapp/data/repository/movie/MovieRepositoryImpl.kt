package comsmilias.example.movieapp.data.repository.movie

import comsmilias.example.movieapp.common.Resource
import comsmilias.example.movieapp.data.repository.movie.datasource.MovieLocalDataSource
import comsmilias.example.movieapp.data.repository.movie.datasource.MovieRemoteDatasource
import comsmilias.example.movieapp.data.repository.movie.datasource.MoviesCacheDataSource
import comsmilias.example.movieapp.domain.model.Movie
import comsmilias.example.movieapp.domain.model.repository.MovieRepository
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDatasource: MovieRemoteDatasource,
    private val moviesCacheDataSource: MoviesCacheDataSource
) : MovieRepository {
    override suspend fun getMovies(refresh: Boolean): List<Movie> {
        return getMoviesFromCache(refresh)
    }

    override suspend fun getMovie(id: Int): Movie {
        return movieLocalDataSource.getMovieFromDb(id)
    }


    private suspend fun getMoviesFromApi(): Resource<List<Movie>> {
        return movieRemoteDatasource.getMovies()
    }

    private suspend fun getMoviesFromDb(refresh: Boolean): List<Movie> {
        lateinit var movieList: List<Movie>
        if (refresh) {
            val movies = getMoviesFromApi()
            if (movies is Resource.Success) {
                movies.data?.let { list ->
                    movieList = list
                    movieLocalDataSource.saveMoviesToDb(list)
                }
            }
        } else {
            try {
                movieList = movieLocalDataSource.getMoviesFromDb()
            } catch (e: Exception) {
                Timber.i(e.message.toString())
            }
            if (movieList.isNotEmpty()) {
                return movieList
            } else {
                val movies = getMoviesFromApi()
                if (movies is Resource.Success) {
                    movies.data?.let { list ->
                        movieList = list
                        movieLocalDataSource.saveMoviesToDb(list)
                    }
                }
            }
        }
        return movieList
    }

    private suspend fun getMoviesFromCache(refresh: Boolean): List<Movie> {
        lateinit var movieList: List<Movie>

        if (refresh) {
            movieList = getMoviesFromDb(true)
            moviesCacheDataSource.saveMoviesToCache(movieList)
        } else {
            try {
                movieList = moviesCacheDataSource.getMoviesFromCache()
            } catch (e: Exception) {
                Timber.i(e.message.toString())
            }
            if (movieList.isNotEmpty()) {
                return movieList
            } else {
                movieList = getMoviesFromDb(false)
                moviesCacheDataSource.saveMoviesToCache(movieList)
            }
        }

        return movieList
    }
}