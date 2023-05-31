package nl.michiel.interview.feature.species.data.api

data class NamedUrl(
    val name: String,
    val url: String,
) {
    fun id(): Long = url.split("/").last { it.isNotEmpty() }.toLong()
}
