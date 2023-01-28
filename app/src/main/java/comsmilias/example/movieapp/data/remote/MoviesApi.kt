package comsmilias.example.movieapp.data.remote

import comsmilias.example.movieapp.data.remote.dto.MovieDto
import retrofit2.Response
import retrofit2.http.GET

interface MoviesApi {

    @GET("shows")
    suspend fun getMovies(): Response<List<MovieDto>>
}