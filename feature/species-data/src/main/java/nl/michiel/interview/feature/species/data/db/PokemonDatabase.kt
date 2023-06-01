package nl.michiel.interview.feature.species.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SpeciesEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun speciesDao(): SpeciesDao
}
