package nl.michiel.interview.feature.species.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import nl.michiel.interview.feature.species.data.api.EvolutionChain
import nl.michiel.interview.feature.species.data.api.NamedUrl
import nl.michiel.interview.feature.species.data.api.PokemonSpecies
import nl.michiel.interview.feature.species.data.api.PokemonSpeciesService
import nl.michiel.interview.feature.species.data.db.SpeciesDao
import nl.michiel.interview.feature.species.data.db.SpeciesEntity
import nl.michiel.interview.feature.species.domain.SpeciesRepository
import nl.michiel.interview.feature.species.domain.entities.Species
import nl.michiel.interview.feature.species.domain.entities.SpeciesDetails

class SpeciesRepositoryImpl(
    private val apiService: PokemonSpeciesService,
    private val database: SpeciesDao,
) : SpeciesRepository {

    private val _speciesFilter = BehaviorSubject.createDefault("")
    override val speciesFilter: Observable<String> = _speciesFilter

    override fun getSpecies(): Observable<List<Species>> {
        return _speciesFilter
            .flatMap { filter ->
                database.getAllWithNameContains(filter)
            }
            .map { entities ->
                entities.map { it.toDomain() }
            }
            .subscribeOn(Schedulers.io())
    }

    //TODO get the capture rate of the next evolution
    override fun getSpeciesDetails(id: Long): Observable<SpeciesDetails> {
        return apiService
            .getPokemonSpecies(id)
            .flatMap { species ->
                apiService
                    .getEvolutionChain(
                        species.evolution_chain?.id() ?: 0
                    ) // we assume the evolution chain is always present
                    .map { chain ->
                        species.toDomain(chain)
                    }
            }
            .flatMap { details ->
                val nextSpeciesId = details.nextEvolution?.id
                if (nextSpeciesId != null) {
                    apiService.getPokemonSpecies(nextSpeciesId)
                        .map { nextSpeciesDetails ->
                            details.copy(nextEvolutionCaptureRate = nextSpeciesDetails.capture_rate)
                        }
                } else {
                    Observable.just(details)
                }
            }
            .subscribeOn(Schedulers.io())
    }

    override fun hasData(): Observable<Boolean> {
        return database
            .count()
            .map { count -> count > 0 }
            .subscribeOn(Schedulers.io())
    }

    override fun setSpeciesFilter(filter: String) {
        _speciesFilter.onNext(filter)
    }

    override fun sync(): Completable {
        val pageSize = 100
        //TODO use WorkManager instead of letting the caller subscribe
        return apiService
            .getPokemonSpeciesPage(limit = pageSize)
            .flatMap { page ->
                // once we've loaded the first page we know how many pages there are
                // create a range of pages and load them all
                Observable
                    .range(1, 1 + (page.count / pageSize))
                    .flatMap { pageNumber ->
                        apiService.getPokemonSpeciesPage(offset = pageNumber * pageSize, limit = pageSize)
                    }
                    // add the first page, the range above starts at 1, so we don't download it twice
                    .startWith(Observable.just(page))
            }
            .subscribeOn(Schedulers.io())
            .doOnNext { page ->
                val entities = page.results.map { it.toSpeciesEntity() }
                database.addAll(entities)
            }
            .ignoreElements()
    }
}

private fun NamedUrl.toSpeciesEntity() = SpeciesEntity(id(), name)

fun PokemonSpecies.toDomain(chain: EvolutionChain): SpeciesDetails {
    return SpeciesDetails(
        species = Species(id, name),
        description = flavorText(),
        captureRate = this.capture_rate,
        genus = this.genusText(),
        growthRate = this.growth_rate?.name ?: "?",
        habitat = this.habitat?.name ?: "?",
        shape = this.shape?.name ?: "?",
        nextEvolution = chain.nextEvolutionOf(name)?.toSpecies(),
    )
}

private fun NamedUrl.toSpecies(): Species = Species(id(), name)
