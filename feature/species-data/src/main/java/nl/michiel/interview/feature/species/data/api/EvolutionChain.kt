package nl.michiel.interview.feature.species.data.api

data class EvolutionChain(
    val id: Long,
    val chain: ChainLink,
) {

    /**
     * NOTE: this implementation assumes that the evolution chain is a simple linked list, no tree (and no cyclic graph)
     */
    fun nextEvolutionOf(speciesName: String): NamedUrl? {
        var candidate = chain
        while (candidate.evolves_to.isNotEmpty()) {
            if (candidate.species.name == speciesName) {
                return candidate.evolves_to.first().species
            }
            candidate = candidate.evolves_to.first()
        }
        return null
    }
}

data class ChainLink(
    val species: NamedUrl,
    val evolves_to: List<ChainLink>,
)
