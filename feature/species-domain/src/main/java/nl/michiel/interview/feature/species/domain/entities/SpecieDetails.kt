package nl.michiel.interview.feature.species.domain.entities

data class SpecieDetails(
    val specie: Specie,
    val description: String,
    val captureRate: Int,
    val nextEvolution: Specie?,
)
