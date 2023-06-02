package nl.michiel.interview.feature.species.data

import nl.michiel.interview.feature.species.data.fixtures.SPECIES_1
import nl.michiel.interview.feature.species.data.fixtures.getBulbasaur
import nl.michiel.interview.feature.species.data.fixtures.getSpeciesPage
import org.junit.Assert.assertEquals
import org.junit.Test

class DeserialiseSpeciesTest {

    @Test
    fun deserialiseList() {
        val listResult = getSpeciesPage()

        assertEquals("total items", 1010, listResult.count)
        assertEquals("items on page", 20, listResult.results.count())
        assertEquals(SPECIES_1, listResult.results.first().name)
        assertEquals("id of bulbasaur", 1, listResult.results.first().id())
        assertEquals("id of #16", 17, listResult.results[16].id())
    }

    @Test
    fun deserialiseBulbasaur() {
        val result = getBulbasaur()

        assertEquals(SPECIES_1, result.name)
        assertEquals("id of bulbasaur", 1, result.id)
        assertEquals("capture rate", 45, result.capture_rate)
        assertEquals("growth rate", "medium-slow", result.growth_rate?.name)
        assertEquals("habitat", "grassland", result.habitat?.name)
        assertEquals("shape", "quadruped", result.shape?.name)
        assertEquals("genera", "Seed Pokémon", result.genusText())
        assertEquals("evolution", 1L, result.evolution_chain?.id())

        val expectedText =
            "A strange seed was\nplanted on its\nback at birth.\nThe plant sprouts\nand grows with\nthis POKéMON."
        assertEquals("flavor text", expectedText, result.flavorText())
    }
}
