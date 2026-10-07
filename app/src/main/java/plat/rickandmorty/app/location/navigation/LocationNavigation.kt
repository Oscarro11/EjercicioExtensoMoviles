package plat.rickandmorty.app.location.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import plat.rickandmorty.app.core.navigation.TopLevelRoute
import plat.rickandmorty.app.location.ui.detail.LocationInfoRoute
import plat.rickandmorty.app.location.ui.list.LocationsRoute

@Serializable data object LocationsGraph : TopLevelRoute
@Serializable data object LocationsDestination
@Serializable
data class LocationInfoDestination(
    val id: Int
)

fun NavController.navigateToLocationsGraph(navOptions: NavOptions? = null) {
    navigate(LocationsGraph, navOptions)
}

private fun NavController.navigateToLocationInfoScreen(
    id: Int,
    navOptions: NavOptions? = null
) {
    navigate(LocationInfoDestination(id), navOptions)
}

fun NavGraphBuilder.locationsGraph(navController: NavController) {
    navigation<LocationsGraph>(startDestination = LocationsDestination){
        locationsScreen(onLocationClick = navController::navigateToLocationInfoScreen)
        locationInfoScreen(onBackClick = navController::navigateUp)
    }
}

private fun NavGraphBuilder.locationsScreen(onLocationClick: (Int) -> Unit) {
    composable<LocationsDestination> {
        LocationsRoute(onLocationClick = onLocationClick)
    }
}

private fun NavGraphBuilder.locationInfoScreen(onBackClick: () -> Unit) {
    composable<LocationInfoDestination> { backStackEntry ->
        val destination = backStackEntry.toRoute<LocationInfoDestination>()

        LocationInfoRoute(
            locationId = destination.id,
            onBackClick = onBackClick
        )
    }
}