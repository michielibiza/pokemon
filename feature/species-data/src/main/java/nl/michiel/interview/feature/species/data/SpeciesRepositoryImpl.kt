package nl.michiel.interview.feature.species.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import nl.michiel.interview.feature.species.data.api.EvolutionChain
import nl.michiel.interview.feature.species.data.api.NamedUrl
import nl.michiel.interview.feature.species.data.api.PokemonSpecies
import nl.michiel.interview.feature.species.data.api.PokemonSpeciesService
import nl.michiel.interview.feature.species.domain.SpeciesRepository
import nl.michiel.interview.feature.species.domain.entities.Species
import nl.michiel.interview.feature.species.domain.entities.SpeciesDetails

class SpeciesRepositoryImpl(
    private val apiService: PokemonSpeciesService,
) : SpeciesRepository {

    //TODO this is a temporary simplistic implementation, we'll add a DB for this later
    override fun getSpecies(): Observable<List<Species>> {
        return apiService
            .getPokemonSpeciesPage()
            .map { page ->
                page.results.map { Species(it.id(), it.name) }
            }
            .subscribeOn(Schedulers.io())
    }

    //TODO get the capture rate of the next evolution
    override fun getSpecies(id: Long): Observable<SpeciesDetails> {
        return apiService
            .getPokemonSpecies(id)
            .flatMap { species ->
                apiService
                    .getEvolutionChain(species.evolution_chain.id())
                    .map { chain ->
                        species.toDomain(chain)
                    }
            }
            .subscribeOn(Schedulers.io())
    }
}

fun PokemonSpecies.toDomain(chain: EvolutionChain): SpeciesDetails {
    return SpeciesDetails(
        species = Species(id, name),
        description = flavorText(),
        captureRate = this.capture_rate,
        nextEvolution = chain.nextEvolutionOf(name)?.toSpecies(),
        genus = this.genusText(),
        growthRate = this.growth_rate.name,
        habitat = this.habitat.name,
        shape = this.shape.name,
    )
}

private fun NamedUrl.toSpecies(): Species = Species(id(), name)
