package plat.rickandmorty.app.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import plat.rickandmorty.app.login.ui.LoginRoute

@Serializable data object LoginDestination

fun NavController.navigateToLoginDestination(navOptions: NavOptions? = null) {
    navigate(LoginDestination, navOptions)
}

fun NavGraphBuilder.loginScreen(
    onLoginClick: () -> Unit
) {
    composable<LoginDestination> {
        LoginRoute(onLoginClick = onLoginClick)
    }
}