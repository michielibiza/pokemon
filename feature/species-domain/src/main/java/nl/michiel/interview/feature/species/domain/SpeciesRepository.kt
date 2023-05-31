package nl.michiel.interview.feature.species.domain

import io.reactivex.rxjava3.core.Observable
import nl.michiel.interview.feature.species.domain.entities.Specie
import nl.michiel.interview.feature.species.domain.entities.SpecieDetails

interface SpeciesRepository {

    fun getSpecies(): Observable<List<Specie>>

    fun getSpecies(id: Long): Observable<SpecieDetails>

}
