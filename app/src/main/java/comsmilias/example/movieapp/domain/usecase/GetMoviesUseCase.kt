package comsmilias.example.movieapp.domain.usecase

import comsmilias.example.movieapp.common.Resource
import comsmilias.example.movieapp.domain.model.Movie
import comsmilias.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(refresh: Boolean): Flow<Resource<List<Movie>>>{
        return flow {
            emit(Resource.Loading())
            emit(repository.getMovies(refresh))
        }
    }
}