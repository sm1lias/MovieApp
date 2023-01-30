package comsmilias.example.movieapp.data.repository.movie

import comsmilias.example.movieapp.common.Resource
import comsmilias.example.movieapp.data.repository.movie.datasource.MovieLocalDataSource
import comsmilias.example.movieapp.data.repository.movie.datasource.MovieRemoteDatasource
import comsmilias.example.movieapp.domain.model.Movie
import comsmilias.example.movieapp.domain.repository.MovieRepository
import timber.log.Timber
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDatasource: MovieRemoteDatasource
) : MovieRepository {
    override suspend fun getMovies(refresh: Boolean): Resource<List<Movie>> {
        return getMoviesFromDb(refresh)
    }


    private suspend fun getMoviesFromApi(): Resource<List<Movie>> {
        return movieRemoteDatasource.getMovies()
    }


    /**
     * Get movies from db
     *
     * @param refresh when true it is getting movies from api and and save to db instead
     * getting immediately from db
     * @return
     */
    private suspend fun getMoviesFromDb(refresh: Boolean): Resource<List<Movie>> {
        lateinit var movieList: List<Movie>
        return if (refresh) {
            getFromApiAndSaveToDb()
        } else {
            try {
                movieList = movieLocalDataSource.getMoviesFromDb()
            } catch (e: Exception) {
                Timber.i(e.message.toString())
            }
            if (movieList.isNotEmpty()) {
                Resource.Success(movieList)
            } else {
                getFromApiAndSaveToDb()
            }
        }
    }

    private suspend fun getFromApiAndSaveToDb(): Resource<List<Movie>> {
        val movies = getMoviesFromApi()
        return try {
            if (movies is Resource.Success) {
                movies.data?.let { list ->
                    movieLocalDataSource.saveMoviesToDb(list)
                    Resource.Success(list)
                } ?: throw Exception("Api returned null data")
            } else {
                Resource.Error(
                    message = movies.message.toString(),
                    data = movieLocalDataSource.getMoviesFromDb()
                )
            }
        } catch (e: Exception) {
            Timber.i(e)
            Resource.Error(message = e.message.toString())
        }
    }

}
