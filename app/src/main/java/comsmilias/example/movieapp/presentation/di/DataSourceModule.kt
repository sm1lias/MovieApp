package comsmilias.example.movieapp.presentation.di

import comsmilias.example.movieapp.data.db.MovieDao
import comsmilias.example.movieapp.data.remote.MoviesApi
import comsmilias.example.movieapp.data.repository.movie.datasource.MovieLocalDataSource
import comsmilias.example.movieapp.data.repository.movie.datasource.MovieRemoteDatasource
import comsmilias.example.movieapp.data.repository.movie.datasourceImpl.MovieLocalDataSourceImpl
import comsmilias.example.movieapp.data.repository.movie.datasourceImpl.MovieRemoteDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(movieDao: MovieDao): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(movieDao)
    }

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(api: MoviesApi): MovieRemoteDatasource {
        return MovieRemoteDatasourceImpl(api)
    }
}