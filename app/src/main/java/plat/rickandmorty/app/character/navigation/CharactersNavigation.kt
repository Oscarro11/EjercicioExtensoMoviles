package plat.rickandmorty.app.character.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import plat.rickandmorty.app.character.ui.creation.CharacterCreationRoute
import plat.rickandmorty.app.character.ui.detail.CharacterInfoRoute
import plat.rickandmorty.app.character.ui.list.CharactersRoute
import plat.rickandmorty.app.core.navigation.TopLevelRoute

@Serializable data object CharactersGraph: TopLevelRoute
@Serializable data object CharactersDestination
@Serializable data class CharacterInfoDestination(
    val id: Int
)
@Serializable data object CharacterCreationDestination

fun NavController.navigateToCharactersGraph(navOptions: NavOptions? = null) {
    navigate(CharactersGraph, navOptions)
}

private fun NavController.navigateToCharacterInfoScreen(
    id: Int,
    navOptions: NavOptions? = null
) {
    navigate(CharacterInfoDestination(id), navOptions)
}

private fun NavController.navigateToCharacterCreationScreen(
    navOptions: NavOptions? = null
) {
    navigate(CharacterCreationDestination, navOptions)
}


fun NavGraphBuilder.charactersGraph(navController: NavController) {
    navigation<CharactersGraph>(startDestination = CharactersDestination){
        charactersScreen(
            onCharacterClick = navController::navigateToCharacterInfoScreen,
            onCreationClick = navController::navigateToCharacterCreationScreen
        )
        characterInfoScreen(onBackClick = navController::navigateUp)
        characterCreationScreen(onBackClick = navController::navigateUp)
    }
}

private fun NavGraphBuilder.charactersScreen(
    onCharacterClick: (Int) -> Unit,
    onCreationClick: () -> Unit,
) {
    composable<CharactersDestination> {
        CharactersRoute(
            onCharacterClick = onCharacterClick,
            onCreationClick = onCreationClick
        )
    }
}

private fun NavGraphBuilder.characterInfoScreen(onBackClick: () -> Unit){
    composable<CharacterInfoDestination> {
        CharacterInfoRoute(
            onBackClick = onBackClick
        )
    }
}

private fun NavGraphBuilder.characterCreationScreen(onBackClick: () -> Unit){
    composable<CharacterCreationDestination> {
        CharacterCreationRoute(
            onBackClick = onBackClick
        )
    }
}