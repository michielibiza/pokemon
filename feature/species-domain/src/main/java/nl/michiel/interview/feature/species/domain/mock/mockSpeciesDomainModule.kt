package nl.michiel.interview.feature.species.domain.mock

import nl.michiel.interview.feature.species.domain.SpeciesRepository
import org.koin.dsl.module

val mockSpeciesDomainModule = module {
    single<SpeciesRepository> { MockSpeciesRepository() }
}
