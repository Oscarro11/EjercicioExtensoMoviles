package plat.rickandmorty.app.splash.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import plat.rickandmorty.app.splash.data.SplashViewModel

@Serializable data object SplashDestination

fun NavGraphBuilder.splashScreen(
    onLoggedIn: () -> Unit,
    onNotLoggedIn: () -> Unit
) {
    composable<SplashDestination> {
        SplashRoute(
            onLoggedIn = onLoggedIn,
            onNotLoggedIn = onNotLoggedIn
        )
    }
}

//TODO: remove custom splash Screen, use androids own splash
@Composable
fun SplashRoute(
    onLoggedIn: () -> Unit,
    onNotLoggedIn: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = viewModel()
){
    LaunchedEffect(Unit) {
        if (viewModel.isLogged()) onLoggedIn() else onNotLoggedIn()
    }

    SplashScreen(
        modifier = modifier
    )
}

//fun
@Composable
private fun SplashScreen( modifier: Modifier = Modifier ){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()

        Text(
            text = "Cargando..."
        )
    }
}