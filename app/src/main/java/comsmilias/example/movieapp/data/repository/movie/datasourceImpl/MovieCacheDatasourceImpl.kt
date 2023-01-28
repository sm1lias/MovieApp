package comsmilias.example.movieapp.data.repository.movie.datasourceImpl

import comsmilias.example.movieapp.data.repository.movie.datasource.MoviesCacheDataSource
import comsmilias.example.movieapp.domain.model.Movie

class MovieCacheDatasourceImpl : MoviesCacheDataSource {
    private var movieList = ArrayList<Movie>()
    override suspend fun getMoviesFromCache(): List<Movie> {
        return movieList
    }

    override suspend fun saveMoviesToCache(movies: List<Movie>) {
        movieList.clear()
        movieList = ArrayList(movies)
    }
}