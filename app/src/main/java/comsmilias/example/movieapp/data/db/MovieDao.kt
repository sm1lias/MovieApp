package comsmilias.example.movieapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import comsmilias.example.movieapp.domain.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<Movie>)

    @Query("DELETE FROM movies_table")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movies_table")
    suspend fun getMovies(): List<Movie>

    @Query("SELECT * FROM movies_table WHERE id = :id LIMIT 1")
    suspend fun getMovie(id: Int): Movie
}