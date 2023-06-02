package nl.michiel.interview.feature.species.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nl.michiel.interview.design.PokemonTheme
import nl.michiel.interview.feature.species.domain.entities.Species
import nl.michiel.interview.feature.species.domain.mock.MockSpeciesRepository
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpeciesListScreen(
    onSpeciesClick: (Species) -> Unit = {},
    viewModel: SpeciesListViewModel = koinViewModel(),
) {
    val species = remember { viewModel.getSpecies() }.subscribeAsState(emptyList())
    val filter = viewModel.speciesFilter.subscribeAsState("")
    val loadState = viewModel.dataState.subscribeAsState(initial = DataState.Unknown)
    SpeciesListScreen(
        species.value,
        filter.value,
        loadState.value,
        onSpeciesClick,
        onFilterChanged = { viewModel.onFilterChanged(it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SpeciesListScreen(
    species: List<Species>,
    filter: String,
    loadState: DataState,
    onSpeciesClick: (Species) -> Unit = {},
    onFilterChanged: (String) -> Unit = {},
) {
    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        ) {
            SearchBar(
                query = filter,
                onQueryChange = onFilterChanged,
                onSearch = {},
                active = false,
                onActiveChange = {},
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                },
                shape = SearchBarDefaults.inputFieldShape,
                modifier = Modifier.fillMaxWidth(),
                content = {}
            )
        }
        when (loadState) {
            is DataState.Error -> ErrorRow(loadState.message)
            DataState.Loading -> LoadingRow()
            else -> Unit
        }
        ShowSpeciesList(species, onSpeciesClick)
    }
}

@Composable
private fun MessageRow(message: String, iconContent: @Composable () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        iconContent()
        Spacer(Modifier.width(8.dp))
        Text(message, color = MaterialTheme.colorScheme.onPrimaryContainer, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun LoadingRow() {
    MessageRow("Loading...") {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun ErrorRow(message: String) {
    MessageRow(message) {
        Icon(
            Icons.Default.Warning,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun ShowSpeciesList(
    species: List<Species>,
    onSpeciesClick: (Species) -> Unit = {},
) {
    LazyColumn() {
        items(species.size) { index ->
            SpeciesCard(species[index], onClick = { onSpeciesClick(species[index]) })
        }
    }
}

@Composable
private fun SpeciesCard(species: Species, onClick: () -> Unit = {}) {
    Row(
        Modifier
            .clickable { onClick() }
            .padding(16.dp, 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (LocalInspectionMode.current) {
            // show dummy in preview
            Icon(
                Icons.Default.Star,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.primary)
            )
        } else {
            AsyncImage(
                model = species.imageUrl,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
            )
        }
        Spacer(Modifier.width(16.dp))
        Text(text = species.name, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun SpeciesCardPreview() {
    PokemonTheme {
        SpeciesCard(MockSpeciesRepository.ivysaur)
    }
}

@Preview(showBackground = true, heightDp = 320)
@Composable
private fun PreviewScreenUpToDate() {
    PokemonTheme {
        SpeciesListScreen(
            listOf(MockSpeciesRepository.ivysaur, MockSpeciesRepository.bulbasaur),
            "filter",
            DataState.UpToDate,
        )
    }
}

@Preview(showBackground = true, heightDp = 320)
@Composable
private fun PreviewScreenLoading() {
    PokemonTheme {
        SpeciesListScreen(
            listOf(MockSpeciesRepository.ivysaur, MockSpeciesRepository.bulbasaur),
            "filter",
            DataState.Loading,
        )
    }
}

@Preview(showBackground = true, heightDp = 320)
@Composable
private fun PreviewScreenError() {
    PokemonTheme {
        SpeciesListScreen(
            listOf(MockSpeciesRepository.ivysaur, MockSpeciesRepository.bulbasaur),
            "filter",
            DataState.Error("no internet"),
        )
    }
}
