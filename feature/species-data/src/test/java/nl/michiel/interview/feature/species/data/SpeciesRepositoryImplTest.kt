package nl.michiel.interview.feature.species.data

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.TestScheduler
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import nl.michiel.interview.feature.species.data.api.PokemonSpeciesService
import nl.michiel.interview.feature.species.data.db.SpeciesDao
import nl.michiel.interview.feature.species.data.fixtures.getBulbasaur
import nl.michiel.interview.feature.species.data.fixtures.getEvolution
import nl.michiel.interview.feature.species.data.fixtures.getSpeciesPage
import org.junit.Test

class SpeciesRepositoryImplTest {

    private val mockApi = mockk<PokemonSpeciesService>()
    private val mockDb = mockk<SpeciesDao>()
    private val scheduler = TestScheduler()

    @Test
    fun `when database is empty hasData returns false`() {
        every { mockDb.count() } returns Observable.just(0)

        val repository = SpeciesRepositoryImpl(mockApi, mockDb)

        assertFalse("has Data", repository.hasData().blockingFirst())
    }

    @Test
    fun `when database has data hasData returns true`() {
        every { mockDb.count() } returns Observable.just(11)

        val repository = SpeciesRepositoryImpl(mockApi, mockDb)

        assertTrue("has Data", repository.hasData().blockingFirst())
    }

    @Test
    fun `when I set the filter then it is used to query the DB`() {
        every { mockDb.getAllWithNameContains(any()) } returns Observable.just(emptyList())

        val repository = SpeciesRepositoryImpl(mockApi, mockDb, scheduler)

        repository.getSpecies().subscribe()
        scheduler.triggerActions()
        repository.setSpeciesFilter("aa")
        scheduler.triggerActions()
        repository.setSpeciesFilter("bb")
        scheduler.triggerActions()

        verify { mockDb.getAllWithNameContains("") }
        verify { mockDb.getAllWithNameContains("aa") }
        verify { mockDb.getAllWithNameContains("bb") }
    }

    @Test
    fun `when sync is called then the API is queried and the DB is updated`() {
        every { mockApi.getPokemonSpeciesPage(any(), any()) } returns Observable.just(getSpeciesPage())
        every { mockDb.addAll(any()) } returns Unit

        val repository = SpeciesRepositoryImpl(mockApi, mockDb, scheduler)

        repository.sync().subscribe()
        scheduler.triggerActions()

        verify(exactly = 11) { mockApi.getPokemonSpeciesPage(any(), 100) }
    }

    @Test
    fun `when the details are requested then the API is called to get the next Evoltion`() {
        every { mockApi.getPokemonSpecies(any()) } returns Observable.just(getBulbasaur())
        every { mockApi.getEvolutionChain(any()) } returns Observable.just(getEvolution())

        val repository = SpeciesRepositoryImpl(mockApi, mockDb, scheduler)

        repository.getSpeciesDetails(1).subscribe()
        scheduler.triggerActions()

        verify { mockApi.getPokemonSpecies(1) }
        verify { mockApi.getEvolutionChain(1) }
        verify { mockApi.getPokemonSpecies(2) }
    }
}
