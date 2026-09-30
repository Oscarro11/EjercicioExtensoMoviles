package plat.rickandmorty.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import plat.rickandmorty.app.R
import plat.rickandmorty.app.character.ui.detail.CharacterInfoRoute
import plat.rickandmorty.app.character.ui.list.CharactersRoute
import plat.rickandmorty.app.location.ui.detail.LocationInfoRoute
import plat.rickandmorty.app.location.ui.list.LocationsRoute
import plat.rickandmorty.app.login.ui.LoginRoute
import plat.rickandmorty.app.profile.ui.ProfileRoute

//Routes for main app
@Serializable data object LoginDestination
@Serializable data object ProfileDestination

//Route for nested graphs
@Serializable data object CharactersNavigationDestination
@Serializable data object LocationsNavigationDestination

//Routes inside nested graph of CharactersNavigationDestination
@Serializable data object CharactersDestination
@Serializable data class CharacterInfoDestination(
    val id: Int
)

//Routes inside nested graph of LocationsNavigationDestination
@Serializable data object LocationsDestination
@Serializable data class LocationInfoDestination(
    val id: Int
)

enum class MainDestination(
    val route: Any,
    val label: String,
    val icon: Int,
    val contentDescription: String? = null
) {
    CHARACTERS(CharactersDestination, "Characters", R.drawable.group_24dp_000000_fill0_wght400_grad0_opsz24),
    LOCATIONS(LocationsDestination, "Locations", R.drawable.globe_24dp_000000_fill0_wght400_grad0_opsz24),
    PROFILE(ProfileDestination, "Profile", R.drawable.person_24dp_000000_fill0_wght400_grad0_opsz24)
}

@Composable
fun AppNavHost(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Any = LoginDestination
){
    NavHost(
        navController = controller,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<LoginDestination> {
            LoginRoute(
                onLoginClick = {
                    controller.navigate(
                        route = CharactersNavigationDestination
                    ) {
                        popUpTo<LoginDestination> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        navigation<CharactersNavigationDestination>(startDestination = CharactersDestination){
            composable<CharactersDestination> {
                CharactersRoute(
                    onCharacterClick = { id: Int ->
                        controller.navigate(
                            route = CharacterInfoDestination(id = id)
                        )
                    }
                )
            }

            composable<CharacterInfoDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute<CharacterInfoDestination>()

                CharacterInfoRoute(
                    characterId = destination.id,
                    onBackClick = {
                        controller.popBackStack()
                    }
                )
            }
        }

        navigation<LocationsNavigationDestination>(startDestination = LocationsDestination){
            composable<LocationsDestination> {
                LocationsRoute(
                    onLocationClick = { id ->
                        controller.navigate(
                            route = LocationInfoDestination(id = id)
                        )
                    }
                )
            }

            composable<LocationInfoDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute<LocationInfoDestination>()

                LocationInfoRoute(
                    locationId = destination.id,
                    onBackClick = {
                        controller.popBackStack()
                    }
                )
            }
        }

        composable<ProfileDestination> {
            ProfileRoute(
                profileId = 0,
                onSessionCloseClick = {
                    controller.navigate(
                        route = LoginDestination
                    ) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}