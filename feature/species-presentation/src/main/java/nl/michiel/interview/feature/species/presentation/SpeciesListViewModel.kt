package nl.michiel.interview.feature.species.presentation

import androidx.lifecycle.ViewModel
import nl.michiel.interview.feature.species.domain.SpeciesRepository

class SpeciesListViewModel(
    private val repository: SpeciesRepository,
) : ViewModel() {

    fun getSpecies() = repository.getSpecies()

}
