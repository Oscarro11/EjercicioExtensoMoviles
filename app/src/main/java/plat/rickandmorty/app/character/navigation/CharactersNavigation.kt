package plat.rickandmorty.app.character.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import plat.rickandmorty.app.character.ui.detail.CharacterInfoRoute
import plat.rickandmorty.app.character.ui.list.CharactersRoute
import plat.rickandmorty.app.core.navigation.TopLevelRoute

@Serializable data object CharactersGraph: TopLevelRoute
@Serializable data object CharactersDestination
@Serializable data class CharacterInfoDestination(
    val id: Int
)

fun NavController.navigateToCharactersGraph(navOptions: NavOptions? = null) {
    navigate(CharactersGraph, navOptions)
}

private fun NavController.navigateToCharacterInfoScreen(
    id: Int,
    navOptions: NavOptions? = null
) {
    navigate(CharacterInfoDestination(id), navOptions)
}


fun NavGraphBuilder.charactersGraph(navController: NavController) {
    navigation<CharactersGraph>(startDestination = CharactersDestination){
        charactersScreen(onCharacterClick = navController::navigateToCharacterInfoScreen)
        characterInfoScreen(onBackClick = navController::navigateUp)
    }
}

private fun NavGraphBuilder.charactersScreen(onCharacterClick: (Int) -> Unit) {
    composable<CharactersDestination> {
        CharactersRoute(onCharacterClick = onCharacterClick)
    }
}

private fun NavGraphBuilder.characterInfoScreen(onBackClick: () -> Unit){
    composable<CharacterInfoDestination> {
        CharacterInfoRoute(
            onBackClick = onBackClick
        )
    }
}