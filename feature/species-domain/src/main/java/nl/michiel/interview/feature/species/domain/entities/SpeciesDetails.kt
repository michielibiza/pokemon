package nl.michiel.interview.feature.species.domain.entities

data class SpeciesDetails(
    val species: Species,
    val description: String?,
    val captureRate: Int,
    val genus: String?,
    val growthRate: String,
    val habitat: String,
    val shape: String,
    val nextEvolution: Species?,
    val nextEvolutionCaptureRate: Int? = null,
)
