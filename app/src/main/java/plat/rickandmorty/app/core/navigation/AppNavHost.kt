package plat.rickandmorty.app.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import plat.rickandmorty.app.R
import plat.rickandmorty.app.character.navigation.CharactersDestination
import plat.rickandmorty.app.character.navigation.CharactersGraph
import plat.rickandmorty.app.character.navigation.charactersGraph
import plat.rickandmorty.app.character.navigation.navigateToCharactersGraph
import plat.rickandmorty.app.location.navigation.LocationsGraph
import plat.rickandmorty.app.location.navigation.locationsGraph
import plat.rickandmorty.app.login.navigation.LoginDestination
import plat.rickandmorty.app.login.navigation.loginScreen
import plat.rickandmorty.app.login.navigation.navigateToLoginDestination
import plat.rickandmorty.app.profile.navigation.ProfileDestination
import plat.rickandmorty.app.profile.navigation.profileScreen
import plat.rickandmorty.app.splash.ui.SplashDestination
import plat.rickandmorty.app.splash.ui.splashScreen

interface TopLevelRoute

enum class TopLevelDestination(
    val route: TopLevelRoute,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    CHARACTERS(
        route = CharactersGraph,
        icon = R.drawable.users,
        label = R.string.characters_title),

    LOCATIONS(
        route = LocationsGraph,
        icon = R.drawable.world,
        label = R.string.locations_title),

    PROFILE(
        route = ProfileDestination,
        icon = R.drawable.user,
        label = R.string.profile_title)
}

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val selectedDestination = TopLevelDestination.entries.firstOrNull() { destination ->
        currentDestination?.hierarchy?.any { it.hasRoute(destination.route::class) } == true
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            if (selectedDestination != null) {
                AppBottomBar(
                    selectedDestination = selectedDestination,
                    onDestinationClick = { destination ->
                        navController.navigate(
                            destination.route,
                            navOptions {
                                popUpTo(CharactersDestination) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = SplashDestination,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            splashScreen(
                onLoggedIn = {
                    navController.navigateToCharactersGraph(
                        navOptions { popUpTo(SplashDestination) { inclusive = true } }
                    )
                },
                onNotLoggedIn = {
                    navController.navigateToLoginDestination(
                        navOptions { popUpTo(SplashDestination) { inclusive = true } }
                    )
                }
            )

            loginScreen(
                onLoginClick = {
                    navController.navigateToCharactersGraph(
                        navOptions { popUpTo(LoginDestination) { inclusive = true } }
                    )
                }
            )

            charactersGraph(navController = navController)
            locationsGraph(navController = navController)

            profileScreen(
                onSessionCloseClick = {
                    navController.navigateToLoginDestination(
                        navOptions { popUpTo(navController.graph.id) { inclusive = true } }
                    )
                }
            )
        }
    }
}

@Composable
fun AppBottomBar(
    selectedDestination: TopLevelDestination,
    onDestinationClick: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        TopLevelDestination.entries.forEach { destination ->
            val selected = destination == selectedDestination
            NavigationBarItem(
                selected = selected,
                onClick = { onDestinationClick(destination) },
                icon = { Icon(painterResource(destination.icon), contentDescription = null) },
                label = { Text(stringResource(destination.label)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}