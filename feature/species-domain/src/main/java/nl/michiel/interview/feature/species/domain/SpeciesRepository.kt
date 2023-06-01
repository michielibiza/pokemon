package nl.michiel.interview.feature.species.domain

import io.reactivex.rxjava3.core.Observable
import nl.michiel.interview.feature.species.domain.entities.Species
import nl.michiel.interview.feature.species.domain.entities.SpeciesDetails

interface SpeciesRepository {

    fun getSpecies(): Observable<List<Species>>

    fun getSpecies(id: Long): Observable<SpeciesDetails>

}
