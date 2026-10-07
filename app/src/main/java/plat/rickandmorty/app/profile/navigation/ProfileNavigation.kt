package plat.rickandmorty.app.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import plat.rickandmorty.app.core.navigation.TopLevelRoute
import plat.rickandmorty.app.profile.ui.ProfileRoute

@Serializable data object ProfileDestination: TopLevelRoute

fun NavController.navigateToProfileDestination(navOptions: NavOptions? = null) {
    navigate(ProfileDestination, navOptions)
}

fun NavGraphBuilder.profileScreen(
    onSessionCloseClick: () -> Unit
) {
    composable<ProfileDestination> {
        ProfileRoute(
            profileId = 0,
            onSessionCloseClick = onSessionCloseClick
        )
    }
}