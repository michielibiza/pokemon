package nl.michiel.interview.feature.species.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import nl.michiel.interview.feature.species.domain.SpeciesRepository

class SpeciesListViewModel(
    private val repository: SpeciesRepository,

) : ViewModel() {

    private val _dataState = BehaviorSubject.createDefault<DataState>(DataState.Unknown)

    val dataState: Observable<DataState> = _dataState

    init {
        // For simplicity we only sync if there is no data in the DB
        // In a real app we would ideally ask the back end if there is new data
        // otherwise we could sync every time the app is opened,
        // but also store the last sync time, to define a minimum interval
        repository
            .hasData()
            .flatMapCompletable { hasData ->
                if (!hasData) {
                    _dataState.onNext(DataState.Loading)
                    repository.sync()
                        .doOnComplete { _dataState.onNext(DataState.UpToDate) }
                } else {
                    Completable.complete()
                }
            }
            .onErrorResumeNext { error ->
                _dataState.onNext(DataState.Error(error.message ?: "Unknown error"))
                Completable.complete()
            }
            .subscribe()
    }

    fun getSpecies() = repository.getSpecies()

    val speciesFilter
        get() = repository.speciesFilter

    fun onFilterChanged(filter: String) {
        repository.setSpeciesFilter(filter)
    }

}

sealed class DataState {
    object Unknown : DataState()
    object Loading : DataState()
    object UpToDate : DataState()
    data class Error(val message: String) : DataState()
}
