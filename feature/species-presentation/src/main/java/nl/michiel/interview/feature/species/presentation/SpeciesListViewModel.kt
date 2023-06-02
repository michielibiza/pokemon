package nl.michiel.interview.feature.species.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Completable
import nl.michiel.interview.feature.species.domain.SpeciesRepository

class SpeciesListViewModel(
    private val repository: SpeciesRepository,
) : ViewModel() {

    init {
        // For simplicity we only sync if there is no data in the DB
        // In a real app we would ideally ask the back end if there is new data
        // otherwise we could sync every time the app is opened,
        // but also store the last sync time, to define a minimum interval
        repository
            .hasData()
            .flatMapCompletable { hasData ->
                if (!hasData) {
                    repository.sync()
                } else {
                    Completable.complete()
                }
            }
            .subscribe()
    }

    fun getSpecies() = repository.getSpecies()

}
