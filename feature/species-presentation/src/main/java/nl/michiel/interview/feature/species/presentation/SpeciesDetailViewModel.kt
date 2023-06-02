package nl.michiel.interview.feature.species.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import nl.michiel.interview.feature.species.domain.SpeciesRepository
import nl.michiel.interview.feature.species.domain.entities.SpeciesDetails

class SpeciesDetailViewModel(
    private val repository: SpeciesRepository,
) : ViewModel() {

    fun getSpeciesDetails(id: Long): Observable<ViewState> {
        return repository.getSpeciesDetails(id)
            .map { ViewState.Data(it) as ViewState }
            .onErrorReturn {
                ViewState.Error(it.message ?: "Unknown error")
            }
            .startWith(Observable.just(ViewState.Loading))
    }

}

sealed class ViewState {
    object Loading : ViewState()
    data class Error(val message: String) : ViewState()
    data class Data(val speciesDetails: SpeciesDetails) : ViewState()
}
