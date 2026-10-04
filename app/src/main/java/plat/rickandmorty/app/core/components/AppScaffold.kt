package plat.rickandmorty.app.core.components

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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import plat.rickandmorty.app.core.navigation.AppNavHost
import plat.rickandmorty.app.core.navigation.CharactersNavigationDestination
import plat.rickandmorty.app.core.navigation.LocationsNavigationDestination
import plat.rickandmorty.app.core.navigation.LoginDestination
import plat.rickandmorty.app.core.navigation.MainDestination
import plat.rickandmorty.app.core.navigation.ProfileDestination

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    startDestination: Any = LoginDestination
){
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val showBottomBar = currentDestination?.hasRoute<LoginDestination>() != true

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ){
                    MainDestination.entries.forEach { destination ->
                        val selected = currentDestination
                            ?.hierarchy
                            ?.any { navDestination ->
                                when (destination) {
                                    MainDestination.CHARACTERS ->
                                        navDestination
                                            .hasRoute<CharactersNavigationDestination>()

                                    MainDestination.LOCATIONS ->
                                        navDestination
                                            .hasRoute<LocationsNavigationDestination>()

                                    MainDestination.PROFILE ->
                                        navDestination
                                            .hasRoute<ProfileDestination>()
                                }
                            } == true

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(
                                    route = destination.route
                                ) {
                                    popUpTo(0)
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = destination.icon),
                                    contentDescription = destination.contentDescription,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            },
                            label = {
                                Text(
                                    text = destination.label,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onPrimaryFixedVariant,
                            )
                        )
                    }
                }
            }
        }
    ) {
        contentPadding ->
        AppNavHost(
            controller = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(contentPadding)
        )
    }
}