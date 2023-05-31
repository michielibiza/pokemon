package nl.michiel.interview.feature.species.data.api

data class EvolutionChain(
    val id: Long,
    val chain: ChainLink,
)

data class ChainLink(
    val species: NamedUrl,
    val evolves_to: List<ChainLink>,
) {
    fun flatten(): List<NamedUrl> = listOf(species) + evolves_to.flatMap { it.flatten() }
}
