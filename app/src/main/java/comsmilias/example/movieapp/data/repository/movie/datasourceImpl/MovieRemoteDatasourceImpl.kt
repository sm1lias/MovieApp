package comsmilias.example.movieapp.data.repository.movie.datasourceImpl

import comsmilias.example.movieapp.common.Resource
import comsmilias.example.movieapp.data.remote.MoviesApi
import comsmilias.example.movieapp.data.remote.dto.MovieDto
import comsmilias.example.movieapp.data.remote.dto.toMovie
import comsmilias.example.movieapp.data.repository.movie.datasource.MovieRemoteDatasource
import comsmilias.example.movieapp.domain.model.Movie
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class MovieRemoteDatasourceImpl @Inject constructor(
    private val api: MoviesApi
) : MovieRemoteDatasource {

    override suspend fun getMovies(): Resource<List<Movie>> {
        return try {
            moviesToResource(api.getMovies())
        } catch (e: IOException) {
            Resource.Error("No internet connection")
        } catch (e: HttpException) {
            Resource.Error(e.message())
        }
    }

    private fun moviesToResource(response: Response<List<MovieDto>>): Resource<List<Movie>> {
        if (response.isSuccessful) {
            response.body()?.let { movieDtos ->
                return Resource.Success(movieDtos.map { movieDto -> movieDto.toMovie() })
            }
        }
        return Resource.Error(response.message())
    }
}