package nl.michiel.interview.feature.species.data

import nl.michiel.interview.feature.species.data.fixtures.SPECIES_1
import nl.michiel.interview.feature.species.data.fixtures.SPECIES_2
import nl.michiel.interview.feature.species.data.fixtures.SPECIES_3
import nl.michiel.interview.feature.species.data.fixtures.getEvolution
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DeserialiseEvolutionTest {

    @Test
    fun deserialiseEvolution() {
        val result = getEvolution()

        val firstSpecies = result.chain.species
        assertEquals("first species", "bulbasaur", firstSpecies.name)
        assertEquals("first species", 1, firstSpecies.id())

        val secondSpecies = result.chain.evolves_to.first().species
        assertEquals("second species", "ivysaur", secondSpecies.name)
        assertEquals("second species", 2, secondSpecies.id())


        val thirdSpecies = result.chain.evolves_to.first().evolves_to.first().species
        assertEquals("third species", "venusaur", thirdSpecies.name)
        assertEquals("third species", 3, thirdSpecies.id())

        assertTrue("no fourth species", result.chain.evolves_to.first().evolves_to.first().evolves_to.isEmpty())
    }

    @Test
    fun nextEvolutionOfTest() {
        val result = getEvolution()

        assertEquals("first evolution", SPECIES_2, result.nextEvolutionOf(SPECIES_1)?.name)
        assertEquals("second evolution", SPECIES_3, result.nextEvolutionOf(SPECIES_2)?.name)
        assertNull("no third evolution", result.nextEvolutionOf(SPECIES_3)?.name)
    }
}
