package plat.rickandmorty.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import plat.rickandmorty.app.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = LoginScreenDestination,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable<LoginScreenDestination> {
                            LoginScreen(
                                onLoginClick = {
                                    navController.navigate(
                                        route = CharactersScreenDestination
                                    ) {
                                        popUpTo<LoginScreenDestination> {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        composable<CharactersScreenDestination> {
                            CharactersScreen(
                                onCharacterClick = { id: Int ->
                                    navController.navigate(
                                        route = CharacterInfoScreenDestination(id = id)
                                    )
                                }
                            )
                        }

                        composable<CharacterInfoScreenDestination> { backStackEntry ->
                            val destination = backStackEntry.toRoute<CharacterInfoScreenDestination>()
                            val character = CharacterDb.getCharacterById(destination.id)

                            CharacterInfoScreen(
                                character = character,
                                onBackClick = {
                                    navController.navigate(
                                        route = CharactersScreenDestination
                                    ) {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Serializable
data object LoginScreenDestination

@Serializable
data object CharactersScreenDestination

@Serializable
data class CharacterInfoScreenDestination(
    val id: Int
)