package nl.michiel.interview.feature.species.domain.entities

data class SpeciesDetails(
    val species: Species,
    val description: String,
    val captureRate: Int,
    val nextEvolution: Species?,
)
