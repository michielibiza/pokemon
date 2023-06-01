package nl.michiel.interview.feature.species.domain.mock

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import nl.michiel.interview.feature.species.domain.SpeciesRepository
import nl.michiel.interview.feature.species.domain.entities.Species
import nl.michiel.interview.feature.species.domain.entities.SpeciesDetails
import java.util.concurrent.TimeUnit

class MockSpeciesRepository : SpeciesRepository {
    override fun getSpecies(): Observable<List<Species>> {
        return Observable.just(listOf(bulbasaur, ivysaur, venusaur))
            .delay(500, TimeUnit.MILLISECONDS)
    }

    override fun getSpecies(id: Long): Observable<SpeciesDetails> {
        return Observable.just(
            SpeciesDetails(
                bulbasaur,
                DESCRIPTION,
                CAPTURE_RATE,
                "Seed",
                "Medium",
                "Grassland",
                "Quadruped",
                ivysaur,
            )
        )
            .delay(300, TimeUnit.MILLISECONDS)
    }

    override fun sync(): Completable {
        return Completable.complete()
    }

    companion object {
        val bulbasaur = Species(
            1,
            "Bulbasaur",
        )
        val ivysaur = Species(
            2,
            "Ivysaur",
        )
        val venusaur = Species(
            3,
            "Venusaur",
        )

        const val DESCRIPTION =
            "A strange seed was planted on its back at birth. The plant sprouts and grows with this Pokémon."
        const val CAPTURE_RATE = 45

    }
}
