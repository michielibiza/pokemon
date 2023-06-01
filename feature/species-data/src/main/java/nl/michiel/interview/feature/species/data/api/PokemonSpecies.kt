package nl.michiel.interview.feature.species.data.api

data class PokemonSpecies(
    val id: Long,
    val name: String,
    val capture_rate: Int,
    val flavor_text_entries: List<FlavorTextEntry>,
    val genera: List<Genera>,
    val growth_rate: NamedUrl,
    val habitat: NamedUrl,
    val shape: NamedUrl,
    val evolution_chain: NamedUrl,
) {
    fun flavorText(language: String = DEFAULT_LANGUAGE, version: String = DEFAULT_VERSION) = flavor_text_entries
        .firstOrNull { it.language.name == language && it.version.name == version }
        ?.flavor_text
        ?.replace("\u000c", "\n")

    fun genusText(language: String = DEFAULT_LANGUAGE) = genera.firstOrNull { it.language.name == language }?.genus

    companion object {
        const val DEFAULT_LANGUAGE = "en"
        const val DEFAULT_VERSION = "red"
    }
}

data class FlavorTextEntry(
    val flavor_text: String,
    val language: NamedUrl,
    val version: NamedUrl,
)

data class Genera(
    val genus: String,
    val language: NamedUrl,
)
