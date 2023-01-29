package comsmilias.example.movieapp.domain.usecase

import comsmilias.example.movieapp.common.Resource
import comsmilias.example.movieapp.domain.model.Movie
import comsmilias.example.movieapp.domain.repository.MovieRepository
import comsmilias.example.movieapp.presentation.movies.MoviesListState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) {
    suspend operator fun invoke(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading())
            getMoviesUseCase(false).collect{ result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { movieList ->
                            emit(Resource.Success(movieList.first { it.id == id }))
                        } ?: emit(Resource.Error("Couldn't retrieve"))
                    }
                    is Resource.Error -> {
                        emit(Resource.Error(result.message ?: "An unexpected error occurred"))

                    }
                    is Resource.Loading -> {
                        emit(Resource.Loading())
                    }
                }
            }
        }
    }
}