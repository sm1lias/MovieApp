package comsmilias.example.movieapp.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.openweatherapp.logs.HTTPLogger
import comsmilias.example.movieapp.common.Constants
import comsmilias.example.movieapp.data.db.MovieDao
import comsmilias.example.movieapp.data.db.MovieDatabase
import comsmilias.example.movieapp.data.remote.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context.applicationContext
    }

    @Provides
    @Singleton
    fun provideRetrofitApi(client: OkHttpClient): MoviesApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        val logging = HttpLoggingInterceptor(HTTPLogger())
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(context: Context): MovieDao {
        return Room.databaseBuilder(context, MovieDatabase::class.java,
            "movie_database")
            .build()
            .movieDao()
    }

}