package nl.michiel.interview.feature.species.data

import nl.michiel.interview.feature.species.data.fixtures.bulbasaurJson
import nl.michiel.interview.feature.species.data.fixtures.specieAdapter
import nl.michiel.interview.feature.species.data.fixtures.speciesPageAdapter
import nl.michiel.interview.feature.species.data.fixtures.speciesPageJson
import org.junit.Assert.assertEquals
import org.junit.Test

class DeserialiseSpeciesTest {

    @Test
    fun deserialiseList() {
        val listResult = speciesPageAdapter.fromJson(speciesPageJson)!!

        assertEquals("total items", 1010, listResult.count)
        assertEquals("items on page", 20, listResult.results.count())
        assertEquals("bulbasaur", listResult.results.first().name)
        assertEquals("id of bulbasaur", 1, listResult.results.first().id())
        assertEquals("id of #16", 17, listResult.results[16].id())
    }

    @Test
    fun deserialiseBulbasaur() {
        val result = specieAdapter.fromJson(bulbasaurJson)!!

        assertEquals("bulbasaur", result.name)
        assertEquals("id of bulbasaur", 1, result.id)
        assertEquals("capture rate", 45, result.capture_rate)
        assertEquals("growth rate", "medium-slow", result.growth_rate.name)
        assertEquals("habitat", "grassland", result.habitat.name)
        assertEquals("shape", "quadruped", result.shape.name)
        assertEquals("genera", "Seed Pokémon", result.genusText())
        assertEquals("evolution", 1, result.evolution_chain.id())

        val expectedText =
             "A strange seed was\nplanted on its\nback at birth.\n\nThe plant sprouts\nand grows with\nthis POKéMON."
        assertEquals("flavor text", expectedText, result.flavorText())
    }
}
