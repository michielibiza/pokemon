package nl.michiel.interview.feature.species.presentation

import androidx.lifecycle.ViewModel
import nl.michiel.interview.feature.species.domain.SpeciesRepository

class SpeciesDetailViewModel(
    private val repository: SpeciesRepository,
) : ViewModel() {

    fun getSpeciesDetails(id: Long) = repository.getSpeciesDetails(id)

}
