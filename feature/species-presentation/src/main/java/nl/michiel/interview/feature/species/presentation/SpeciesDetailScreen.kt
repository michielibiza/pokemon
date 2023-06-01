package nl.michiel.interview.feature.species.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
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
import nl.michiel.interview.feature.species.domain.entities.SpeciesDetails
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpeciesDetailScreen(
    id: Long,
    name: String,
    viewModel: SpeciesDetailViewModel = koinViewModel()
) {
    val details = viewModel.getSpecies(id).subscribeAsState(initial = null)
    SpeciesDetailScreen(name, details.value)
}

@Composable
fun SpeciesDetailScreen(name: String, details: SpeciesDetails?) {
    if (details == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Row {
                CircularProgressIndicator()
                Spacer(Modifier.width(8.dp))
                Text(text = "loading $name")
            }
        }
        return
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 24.dp)
    ) {
        Row {
            Text(
                details.description,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp),
            )
            Spacer(Modifier.weight(1f))
            val imageModifier = Modifier
                .size(96.dp)
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium)
            if (LocalInspectionMode.current) {
                // show dummy in preview
                Box(imageModifier.background(MaterialTheme.colorScheme.secondary))
            } else {
                AsyncImage(details.species.imageUrl, "", imageModifier)
            }
        }
        Spacer(Modifier.height(24.dp))
        Text(
            "Capture rate: ${details.captureRate}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(8.dp))
        details.nextEvolution?.let { nextEvolution ->
            Text(
                "Evolves into ${nextEvolution.name}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewDetails() {
    PokemonTheme {
        SpeciesDetailScreen(
            "bulbasaur",
            SpeciesDetails(
                Species(1, "bulbasaur"),
                "A strange seed was\nplanted on its\nback at birth.\n\nThe plant sprouts\nand grows with\nthis POKéMON.",
                45,
                Species(2, "ivysaur"),
            )
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewDetailsEmpty() {
    PokemonTheme {
        SpeciesDetailScreen(
            "bulbasaur",
            null
        )
    }
}
