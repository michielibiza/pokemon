package nl.michiel.interview.feature.species.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nl.michiel.interview.design.Green
import nl.michiel.interview.design.PokemonTheme
import nl.michiel.interview.design.Red
import nl.michiel.interview.feature.species.domain.entities.Species
import nl.michiel.interview.feature.species.domain.entities.SpeciesDetails
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpeciesDetailScreen(
    id: Long,
    name: String,
    viewModel: SpeciesDetailViewModel = koinViewModel()
) {
    val speciesObservable = remember { viewModel.getSpeciesDetails(id) }
    val speciesState by speciesObservable.subscribeAsState(initial = ViewState.Loading)
    SpeciesDetailScreen(name, speciesState)
}

@Composable
fun SpeciesDetailScreen(name: String, speciesState: ViewState) {
    when (speciesState) {
        is ViewState.Error -> ErrorScreen(message = speciesState.message)
        is ViewState.Loading -> LoadingScreen(name)
        is ViewState.Data -> SpeciesDetailScreen(speciesState.speciesDetails)
    }
}

@Composable
fun LoadingScreen(name: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row {
            CircularProgressIndicator()
            Spacer(Modifier.width(8.dp))
            Text(text = "loading $name")
        }
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(56.dp))
            Spacer(Modifier.width(8.dp))
            Text(message)
        }
    }
}

@Composable
fun SpeciesDetailScreen(details: SpeciesDetails) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Avatar(details.species.imageUrl)
            Spacer(Modifier.width(16.dp))
            Description(details, Modifier.weight(1f))
        }
        Spacer(Modifier.height(24.dp))
        PropertyText(R.string.details_genus, details.genus)
        PropertyText(R.string.details_habitat, details.habitat)
        PropertyText(R.string.details_shape, details.shape)
        PropertyText(R.string.details_growth_rate, details.growthRate)
        PropertyText(R.string.details_capture_rate, details.captureRate.toString())
        details.nextEvolution?.let { nextEvolution ->
            Spacer(modifier = Modifier.height(24.dp))
            Text(stringResource(R.string.details_next_evolution_title), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Column(Modifier.weight(1f)) {
                    Text(nextEvolution.name, style = MaterialTheme.typography.displaySmall)
                    val newRate = details.nextEvolutionCaptureRate ?: 0 // can't be null
                    EvolutionCaptureRate(newRate, details.captureRate)
                }
                Spacer(Modifier.width(16.dp))
                Avatar(nextEvolution.imageUrl)
            }
        }
    }
}

@Composable
private fun EvolutionCaptureRate(newRate: Int, oldRate: Int) {
    Row(Modifier.padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(R.string.details_capture_rate), style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.weight(1f))
        Text("$newRate", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.width(8.dp))
        RateChangeText(newRate - oldRate)
    }
}

@Composable
fun RateChangeText(rateChange: Int) {
    if (rateChange == 0) {
        return
    }

    val color = if (rateChange > 0) Green else Red
    val text = if (rateChange > 0) "(+$rateChange)" else "($rateChange)"
    Text(text, color = color, style = MaterialTheme.typography.bodySmall)
}

@Composable
private fun Description(details: SpeciesDetails, modifier: Modifier = Modifier) {
    val description = details.description?.replace("\n", " ")
        ?: stringResource(id = R.string.details_no_description)

    Text(
        description,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier,
    )
}

@Composable
private fun Avatar(avatarUrl: String) {
    val imageModifier = Modifier
        .size(96.dp)
        .clip(MaterialTheme.shapes.medium)
    if (LocalInspectionMode.current) {
        // show dummy in preview
        Box(imageModifier.background(MaterialTheme.colorScheme.secondary))
    } else {
        AsyncImage(avatarUrl, "", imageModifier)
    }
}

@Composable
fun PropertyText(@StringRes name: Int, value: String?) {
    Row(Modifier.padding(bottom = 8.dp)) {
        Text(stringResource(name), style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.weight(1f))
        Text(value ?: "?", style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewDetails() {
    PokemonTheme {
        SpeciesDetailScreen(
            "bulbasaur",
            ViewState.Data(
                SpeciesDetails(
                    Species(1, "bulbasaur"),
                    "A strange seed was\nplanted on its\nback at birth.\nThe plant sprouts\nand grows with\nthis POKéMON.",
                    120,
                    "Seed",
                    "medium-slow",
                    "grassland",
                    "quadruped",
                    Species(2, "ivysaur"),
                    45,
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRateChange() {
    PokemonTheme {
        EvolutionCaptureRate(120, 120)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRateChangeUp() {
    PokemonTheme {
        EvolutionCaptureRate(120, 45)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRateChangeDown() {
    PokemonTheme {
        EvolutionCaptureRate(120, 255)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 240)
@Composable
fun PreviewDetailsLoading() {
    PokemonTheme {
        SpeciesDetailScreen(
            "bulbasaur",
            ViewState.Loading
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 240)
@Composable
fun PreviewDetailsError() {
    PokemonTheme {
        SpeciesDetailScreen(
            "bulbasaur",
            ViewState.Error("no internet")
        )
    }
}
