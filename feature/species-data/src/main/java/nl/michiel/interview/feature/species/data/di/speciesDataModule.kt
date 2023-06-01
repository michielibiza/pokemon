package nl.michiel.interview.feature.species.data.di

import androidx.room.Room
import nl.michiel.interview.feature.species.data.SpeciesRepositoryImpl
import nl.michiel.interview.feature.species.data.api.PokemonSpeciesService
import nl.michiel.interview.feature.species.data.db.PokemonDatabase
import nl.michiel.interview.feature.species.domain.SpeciesRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val speciesDataModule = module {

    single<SpeciesRepository> { SpeciesRepositoryImpl(get(), get()) }

    // Room DB
    single<PokemonDatabase> {
        Room.databaseBuilder(get(), PokemonDatabase::class.java, "pokemon-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    // Room DAO
    single { get<PokemonDatabase>().speciesDao() }

    // Retrofit service
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(client)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        retrofit.create(PokemonSpeciesService::class.java)
    }

}
