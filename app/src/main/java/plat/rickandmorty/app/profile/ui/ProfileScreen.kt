package plat.rickandmorty.app.profile.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import plat.rickandmorty.app.R
import plat.rickandmorty.app.core.components.IconWithBackground
import plat.rickandmorty.app.core.components.InfoRow
import plat.rickandmorty.app.profile.data.ProfileState
import plat.rickandmorty.app.profile.data.ProfileViewModel

@Composable
fun ProfileRoute(
    onSessionCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProfileScreen(
        iconId = R.drawable.user,
        state = state,
        modifier = modifier,
        onSessionCloseClick = { viewModel.logout(onDone = onSessionCloseClick) }
    )
}

@Composable
private fun ProfileScreen(
    @DrawableRes iconId: Int,
    state: ProfileState,
    modifier: Modifier = Modifier,
    onSessionCloseClick: () -> Unit = {}
){
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        IconWithBackground(
            iconId = iconId,
            size = 160.dp,
            backgroundColor = MaterialTheme.colorScheme.primary,
            backgroundShape = CircleShape,
            tintColor = MaterialTheme.colorScheme.onPrimary,
            iconScale = 0.8f,
            modifier = Modifier.padding(24.dp)
        )

        InfoRow(
            label = "Nombre",
            data = state.name,
            modifier = Modifier.padding(vertical = 6.dp)
        )

        InfoRow(
            label = "Carné",
            data = state.license,
            modifier = Modifier.padding(vertical = 6.dp)
        )

        Button(
            onClick = onSessionCloseClick,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Cerrar Sesión",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen(){
    ProfileScreen(
        iconId = R.drawable.user,
        state = ProfileState(
            name = "Oscar"
        )
    )
}