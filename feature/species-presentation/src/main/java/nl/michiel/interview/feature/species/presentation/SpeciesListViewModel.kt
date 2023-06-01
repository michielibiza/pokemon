package nl.michiel.interview.feature.species.presentation

import androidx.lifecycle.ViewModel
import nl.michiel.interview.feature.species.domain.SpeciesRepository

class SpeciesListViewModel(
    private val repository: SpeciesRepository,
) : ViewModel() {

    init {
        //TODO only do this once a day
        //ideally you would check if the data is stale, by querying the API
        repository.sync().subscribe()
    }

    fun getSpecies() = repository.getSpecies()

}
