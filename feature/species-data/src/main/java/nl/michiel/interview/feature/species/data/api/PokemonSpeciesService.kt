package nl.michiel.interview.feature.species.data.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface PokemonSpeciesService {

    @GET("pokemon-species")
    fun getPokemonSpeciesPage(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20,
    ): Observable<PokemonSpeciesPage>

    @GET("pokemon-species/{id}")
    fun getPokemonSpecies(
        @Query("id") id: Long,
    ): Observable<PokemonSpecies>

    @GET("evolution-chain/{id}")
    fun getEvolutionChain(
        @Query("id") id: Long,
    ): Observable<EvolutionChain>

}
