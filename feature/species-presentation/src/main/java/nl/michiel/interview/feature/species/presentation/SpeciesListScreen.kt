package nl.michiel.interview.feature.species.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val species = viewModel.getSpecies().subscribeAsState(emptyList())
    SpeciesListScreen(species.value, onSpeciesClick)
}

@Composable
fun SpeciesListScreen(
    species: List<Species>,
    onSpeciesClick: (Species) -> Unit = {},
) {
    LazyColumn {
        items(species.size) { index ->
            SpeciesCard(species[index], onClick = { onSpeciesClick(species[index]) })
        }
    }
}

@Composable
fun SpeciesCard(species: Species, onClick: () -> Unit = {}) {
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
fun SpeciesCardPreview() {
    PokemonTheme {
        SpeciesCard(MockSpeciesRepository.ivysaur)
    }
}
