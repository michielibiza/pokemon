package nl.michiel.interview.pokemon.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.michiel.interview.design.PokemonTheme
import nl.michiel.interview.feature.species.domain.entities.Species
import nl.michiel.interview.feature.species.presentation.SpeciesDetailScreen
import nl.michiel.interview.feature.species.presentation.SpeciesListScreen
import nl.michiel.interview.pokemon.R
import nl.michiel.interview.pokemon.view.Routes.DETAIL_ARG_ID
import nl.michiel.interview.pokemon.view.Routes.DETAIL_ARG_NAME

object Routes {
    const val DETAIL_ARG_ID = "id"
    const val DETAIL_ARG_NAME = "name"
    const val DETAIL_BASE = "specieDetail"

    const val LIST = "speciesList"
    const val DETAIL = "$DETAIL_BASE/{$DETAIL_ARG_ID}/{$DETAIL_ARG_NAME}"

    val detailArgs = listOf(
        navArgument(DETAIL_ARG_ID) { type = NavType.LongType },
        navArgument(DETAIL_ARG_NAME) { type = NavType.StringType },
    )

    fun detail(species: Species) = "$DETAIL_BASE/${species.id}/${species.name}"
}


@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val title = navController.currentBackStackEntryAsState().value?.let { navBackStackEntry ->
        navBackStackEntry.destination.route?.let { route ->
            if (route.startsWith(Routes.DETAIL_BASE)) {
                navBackStackEntry.arguments?.getString(DETAIL_ARG_NAME)
            } else {
                null
            }
        }
    } ?: stringResource(R.string.app_name)

    PokemonTheme {
        Scaffold(
            topBar = { AppBar(navController, title) }
        ) { padding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                color = MaterialTheme.colorScheme.background,
            ) {
                NavHost(navController, startDestination = Routes.LIST) {
                    composable(Routes.LIST) {
                        SpeciesListScreen(onSpeciesClick = { specie ->
                            navController.navigate(Routes.detail(specie))
                        })
                    }
                    composable(Routes.DETAIL, arguments = Routes.detailArgs) { backStackEntry ->
                        backStackEntry.arguments
                            ?.let { args ->
                                val id = args.getLong(DETAIL_ARG_ID)
                                val name = args.getString(DETAIL_ARG_NAME) ?: ""
                                SpeciesDetailScreen(id, name)
                            }
                            ?: throw IllegalStateException("Missing arguments")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    navController: androidx.navigation.NavController,
    title: String,
) {
    TopAppBar(
        title = { Text(text = title) },
        colors = with(MaterialTheme.colorScheme) {
            TopAppBarDefaults.topAppBarColors(
                containerColor = primary,
                titleContentColor = onPrimary,
                navigationIconContentColor = onPrimary,
                actionIconContentColor = onPrimary,
            )
        },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
    )
}
