package plat.rickandmorty.app.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import plat.rickandmorty.app.R
import plat.rickandmorty.app.login.data.LoginEvent
import plat.rickandmorty.app.login.data.LoginState
import plat.rickandmorty.app.login.data.LoginViewModel

@Composable
fun LoginRoute(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginScreen(
        state = state,
        onLoginClick = { viewModel.login( onDone = onLoginClick ) },
        onEvent = viewModel::onChange,
        modifier = modifier
    )
}
@Composable
private fun LoginScreen(
    state: LoginState,
    onLoginClick: () -> Unit,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.then(
            Modifier.fillMaxSize()
                .padding(16.dp)
        )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource( id = R.drawable.r_m_logo ),
                contentDescription = null
            )

            OutlinedTextField(
                value = state.name,
                onValueChange = { value -> onEvent( LoginEvent.ChangeName(value) ) },
                enabled = !state.isLoading,
                placeholder = { Text("Nombre") },
                modifier = Modifier.padding(12.dp)
            )

            if (!state.isLoading) {
                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = "Entrar"
                    )
                }
            }
            else {
                CircularProgressIndicator()
            }
        }

        Text(
            text = "Oscar Rodrigo Melchor Estrada - #25216"
        )
    }
}