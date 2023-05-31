package nl.michiel.interview.feature.species.data.api

data class PokemonSpeciesPage(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<NamedUrl>
)
