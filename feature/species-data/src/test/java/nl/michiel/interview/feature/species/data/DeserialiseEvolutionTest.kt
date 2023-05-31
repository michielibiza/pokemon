package nl.michiel.interview.feature.species.data

import nl.michiel.interview.feature.species.data.fixtures.evolutionAdapter
import nl.michiel.interview.feature.species.data.fixtures.evolutionJson
import org.junit.Assert.assertEquals
import org.junit.Test

class DeserialiseEvolutionTest {

    @Test
    fun deserialiseEvolution() {
        val result = evolutionAdapter.fromJson(evolutionJson)!!
        val chain = result.chain.flatten()

        assertEquals("chain length", 3, chain.size)
        assertEquals("first specie", "bulbasaur", chain[0].name)
        assertEquals("first specie", 1, chain[0].id())
        assertEquals("second specie", "ivysaur", chain[1].name)
        assertEquals("second specie", 2, chain[1].id())
        assertEquals("third specie", "venusaur", chain[2].name)
        assertEquals("third specie", 3, chain[2].id())
    }
}
