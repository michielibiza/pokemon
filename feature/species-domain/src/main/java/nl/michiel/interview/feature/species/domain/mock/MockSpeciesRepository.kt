package nl.michiel.interview.feature.species.domain.mock

import io.reactivex.rxjava3.core.Observable
import nl.michiel.interview.feature.species.domain.SpeciesRepository
import nl.michiel.interview.feature.species.domain.entities.Specie
import nl.michiel.interview.feature.species.domain.entities.SpecieDetails
import java.util.concurrent.TimeUnit

class MockSpeciesRepository : SpeciesRepository {
    override fun getSpecies(): Observable<List<Specie>> {
        return Observable.just(listOf(bulbasaur, ivysaur, venusaur))
            .delay(500, TimeUnit.MILLISECONDS)
    }

    override fun getSpecies(id: Long): Observable<SpecieDetails> {
        return Observable.just(SpecieDetails(bulbasaur, ivysaur))
            .delay(300, TimeUnit.MILLISECONDS)
    }

    companion object {
        val bulbasaur = Specie(
            1,
            "Bulbasaur",
            "A strange seed was planted on its back at birth. The plant sprouts and grows with this Pokémon.",
            45
        )
        val ivysaur = Specie(
            2,
            "Ivysaur",
            "When the bulb on its back grows large, it appears to lose the ability to stand on its hind legs.",
            45
        )
        val venusaur = Specie(
            3,
            "Venusaur",
            "The plant blooms when it is absorbing solar energy. It stays on the move to seek sunlight.",
            45
        )
    }
}
