package nl.michiel.interview.feature.species.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import nl.michiel.interview.feature.species.domain.entities.Specie
import nl.michiel.interview.feature.species.domain.mock.MockSpeciesRepository
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpeciesListScreen(
    viewModel: SpeciesListViewModel = koinViewModel(),
) {
    val species = viewModel.getSpecies().subscribeAsState(emptyList())
    SpeciesListScreen(species.value)
}

@Composable
fun SpeciesListScreen(species: List<Specie>) {
    LazyColumn {
        items(species.size) { index ->
            SpeciesCard(species[index])
        }
    }
}

@Composable
fun SpeciesCard(specie: Specie) {
    Row(Modifier.padding(16.dp, 8.dp), verticalAlignment = Alignment.CenterVertically) {
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
                model = specie.imageUrl,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
            )
        }
        Spacer(Modifier.width(16.dp))
        Text(text = specie.name, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun SpeciesCardPreview() {
    PokemonTheme {
        SpeciesCard(MockSpeciesRepository.ivysaur)
    }
}
