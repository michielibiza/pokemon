package nl.michiel.interview.feature.species.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import nl.michiel.interview.feature.species.data.api.PokemonSpeciesService
import nl.michiel.interview.feature.species.domain.SpeciesRepository
import nl.michiel.interview.feature.species.domain.entities.Specie
import nl.michiel.interview.feature.species.domain.entities.SpecieDetails

class SpeciesRepositoryImpl(
    private val apiService: PokemonSpeciesService,
) : SpeciesRepository {

    //TODO this is a temporary simplistic implementation, we'll add a DB for this later
    override fun getSpecies(): Observable<List<Specie>> {
        return apiService
            .getPokemonSpeciesPage()
            .map { page ->
                page.results.map { Specie(it.id(), it.name) }
            }
            .subscribeOn(Schedulers.io())
    }

    override fun getSpecies(id: Long): Observable<SpecieDetails> {
        return apiService
            .getPokemonSpecies(id)
            .flatMap { species ->
                apiService
                    .getEvolutionChain(species.evolution_chain.id())
                    .map { chain ->
                        SpecieDetails(
                            specie = Specie(id, species.name),
                            description = species.flavorText() ?: "",
                            captureRate = species.capture_rate,
                            nextEvolution = chain.chain.flatten()
                                .firstOrNull { it.id() != id }
                                ?.let { Specie(it.id(), it.name) },
                        )
                    }
            }
            .subscribeOn(Schedulers.io())
    }
}
