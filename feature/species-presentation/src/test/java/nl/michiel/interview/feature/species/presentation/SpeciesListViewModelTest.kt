package nl.michiel.interview.feature.species.presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import nl.michiel.interview.feature.species.domain.SpeciesRepository
import org.junit.Test

class SpeciesListViewModelTest {

    val mockRepo = mockk<SpeciesRepository>()

    @Test
    fun `when viewmodel is created and there is data, we don't sync`() {
        every { mockRepo.hasData() } returns Observable.just(true)

        SpeciesListViewModel(mockRepo)

        verify(exactly = 0) { mockRepo.sync() }
    }

    @Test
    fun `when viewmodel is created and there is no data, we call sync`() {
        every { mockRepo.hasData() } returns Observable.just(false)

        SpeciesListViewModel(mockRepo)

        verify(exactly = 1) { mockRepo.sync() }
    }

    @Test
    fun `when we sync successful, the load state is UpToDate`() {
        every { mockRepo.hasData() } returns Observable.just(false)
        every { mockRepo.sync() } returns Completable.complete()

        val viewModel = SpeciesListViewModel(mockRepo)
        viewModel.dataState.test().assertValue(DataState.UpToDate)
    }

    @Test
    fun `when sync fails, the load state is to Error`() {
        every { mockRepo.hasData() } returns Observable.just(false)
        every { mockRepo.sync() } throws Exception("error-message")

        val viewModel = SpeciesListViewModel(mockRepo)
        viewModel.dataState.test().assertValue(DataState.Error("error-message"))
    }
}
