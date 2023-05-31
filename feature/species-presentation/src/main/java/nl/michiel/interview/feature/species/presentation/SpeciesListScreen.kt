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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nl.michiel.interview.design.PokemonTheme

@Composable
fun SpeciesListScreen() {
    LazyColumn {
        items(30) {
            SpeciesCard()
        }
    }
}

@Composable
fun SpeciesCard() {
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
                model = MOCK_SPECIES_IMAGE,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
            )
        }
        Spacer(Modifier.width(16.dp))
        Text(text = MOCK_SPECIES_NAME, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun SpeciesCardPreview() {
    PokemonTheme {
        SpeciesCard()
    }
}

private const val MOCK_SPECIES_IMAGE = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
private const val MOCK_SPECIES_NAME = "Bulbasaur"
