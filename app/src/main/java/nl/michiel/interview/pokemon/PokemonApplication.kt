package nl.michiel.interview.pokemon

import android.app.Application
import nl.michiel.interview.feature.species.data.di.speciesDataModule
import nl.michiel.interview.feature.species.presentation.di.speciesPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        setupDI()
    }

    private fun setupDI() {
        startKoin {
            androidContext(this@PokemonApplication)
            androidLogger()
            modules(
                speciesPresentationModule,
                speciesDataModule,
            )
        }
    }
}
