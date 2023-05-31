package nl.michiel.interview.feature.species.domain.entities

data class SpecieDetails(
    val specie: Specie,
    val nextEvolution: Specie,
)
