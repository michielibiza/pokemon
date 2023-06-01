package nl.michiel.interview.feature.species.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.reactivex.rxjava3.core.Observable

@Dao
interface SpeciesDao {

    @Query("SELECT * FROM species")
    fun getAll(): Observable<List<SpeciesEntity>>

    @Query("SELECT * FROM species WHERE name LIKE '%' || :filter || '%'")
    fun getAllWithNameContains(filter: String): Observable<List<SpeciesEntity>>

    @Upsert
    fun addAll(species: List<SpeciesEntity>)
}
