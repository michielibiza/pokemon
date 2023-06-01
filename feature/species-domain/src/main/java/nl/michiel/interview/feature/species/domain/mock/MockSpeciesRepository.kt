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
        return Observable.just(SpecieDetails(bulbasaur, DESCRIPTION, CAPTURE_RATE, ivysaur))
            .delay(300, TimeUnit.MILLISECONDS)
    }

    companion object {
        val bulbasaur = Specie(
            1,
            "Bulbasaur",
        )
        val ivysaur = Specie(
            2,
            "Ivysaur",
        )
        val venusaur = Specie(
            3,
            "Venusaur",
        )

        const val DESCRIPTION =
            "A strange seed was planted on its back at birth. The plant sprouts and grows with this Pokémon."
        const val CAPTURE_RATE = 45

    }
}
