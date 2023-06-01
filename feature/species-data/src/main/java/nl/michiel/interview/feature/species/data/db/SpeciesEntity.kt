package nl.michiel.interview.feature.species.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import nl.michiel.interview.feature.species.domain.entities.Species

@Entity(tableName = "species", indices = [Index(value = ["name"], unique = true)])
data class SpeciesEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
) {
    fun toDomain() = Species(id, name)
}
