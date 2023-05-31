package nl.michiel.interview.feature.species.domain.entities

data class Specie(
    val id: Long,
    val name: String,
    val description: String,
    val captureRate: Int,
) {
    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}
