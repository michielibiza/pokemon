package nl.michiel.interview.feature.species.data.fixtures

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import nl.michiel.interview.feature.species.data.api.PokemonSpecies
import nl.michiel.interview.feature.species.data.api.PokemonSpeciesPage

private val moshi = Moshi.Builder().build()
val speciesPageAdapter: JsonAdapter<PokemonSpeciesPage> = moshi.adapter(PokemonSpeciesPage::class.java)
val specieAdapter: JsonAdapter<PokemonSpecies> = moshi.adapter(PokemonSpecies::class.java)
