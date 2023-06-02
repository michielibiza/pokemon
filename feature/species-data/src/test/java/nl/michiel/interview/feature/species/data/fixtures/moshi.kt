package nl.michiel.interview.feature.species.data.fixtures

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import nl.michiel.interview.feature.species.data.api.EvolutionChain
import nl.michiel.interview.feature.species.data.api.PokemonSpecies
import nl.michiel.interview.feature.species.data.api.PokemonSpeciesPage

private val moshi = Moshi.Builder().build()
val speciesPageAdapter: JsonAdapter<PokemonSpeciesPage> = moshi.adapter(PokemonSpeciesPage::class.java)
val speciesAdapter: JsonAdapter<PokemonSpecies> = moshi.adapter(PokemonSpecies::class.java)
val evolutionAdapter: JsonAdapter<EvolutionChain> = moshi.adapter(EvolutionChain::class.java)
