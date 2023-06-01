package nl.michiel.interview.feature.species.presentation.di

import nl.michiel.interview.feature.species.presentation.SpeciesDetailViewModel
import nl.michiel.interview.feature.species.presentation.SpeciesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val speciesPresentationModule = module {
    viewModel { SpeciesListViewModel(get()) }
    viewModel { SpeciesDetailViewModel(get()) }
}
