package comsmilias.example.movieapp.data.repository.movie

import comsmilias.example.movieapp.common.Resource
import comsmilias.example.movieapp.data.repository.movie.datasource.MovieLocalDataSource
import comsmilias.example.movieapp.data.repository.movie.datasource.MovieRemoteDatasource
import comsmilias.example.movieapp.data.repository.movie.datasource.MoviesCacheDataSource
import comsmilias.example.movieapp.domain.model.Movie
import comsmilias.example.movieapp.domain.repository.MovieRepository
import timber.log.Timber
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDatasource: MovieRemoteDatasource,
    private val moviesCacheDataSource: MoviesCacheDataSource
) : MovieRepository {
    override suspend fun getMovies(refresh: Boolean): Resource<List<Movie>> {
        return getMoviesFromCache(refresh)
    }

    override suspend fun getMovie(id: Int): Resource<Movie> {
        return Resource.Success(movieLocalDataSource.getMovieFromDb(id))
    }


    private suspend fun getMoviesFromApi(): Resource<List<Movie>> {
        return movieRemoteDatasource.getMovies()
    }

    private suspend fun getMoviesFromDb(refresh: Boolean): Resource<List<Movie>> {
        lateinit var movieList: List<Movie>
        if (refresh) {
            val movies = getMoviesFromApi()
            if (movies is Resource.Success) {
                movies.data?.let { list ->
                    movieList = list
                    movieLocalDataSource.saveMoviesToDb(list)
                }
            } else {
                return movies
            }
        } else {
            try {
                movieList = movieLocalDataSource.getMoviesFromDb()
            } catch (e: Exception) {
                Timber.i(e.message.toString())
            }
            if (movieList.isNotEmpty()) {
                return Resource.Success(movieList)
            } else {
                val movies = getMoviesFromApi()
                if (movies is Resource.Success) {
                    movies.data?.let { list ->
                        movieList = list
                        movieLocalDataSource.saveMoviesToDb(list)
                    }
                } else
                    return movies
            }
        }
        return Resource.Success(movieList)
    }

    private suspend fun getMoviesFromCache(refresh: Boolean): Resource<List<Movie>> {
        lateinit var movieList: Resource<List<Movie>>

        if (refresh) {
            movieList = getMoviesFromDb(true)
            if (movieList is Resource.Success)
                movieList.data?.let {
                    moviesCacheDataSource.saveMoviesToCache(it)
                }
        } else {
            try {
                movieList = Resource.Success(moviesCacheDataSource.getMoviesFromCache())
            } catch (e: Exception) {
                Timber.i(e.message.toString())
                return Resource.Error("Couldn't retrieve list")
            }
            if (movieList.data?.isNotEmpty() == true) {
                return movieList
            } else {
                movieList = getMoviesFromDb(false)
                if (movieList is Resource.Success)
                    movieList.data?.let {
                        moviesCacheDataSource.saveMoviesToCache(it)
                    }
            }
        }

        return movieList
    }
}