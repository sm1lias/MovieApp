package comsmilias.example.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import comsmilias.example.movieapp.domain.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
}