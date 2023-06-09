package nl.michiel.interview.feature.species.domain.entities

data class Species(
    val id: Long,
    val name: String,
) {
    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}
