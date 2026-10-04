package plat.rickandmorty.app.chores.ui.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import plat.rickandmorty.app.R
import plat.rickandmorty.app.core.components.darkenColor

@Composable
fun ErrorScreen(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(48.dp)
            .fillMaxSize()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.error),
            contentDescription = null,
            tint = darkenColor(originalColor = Color.Red, factor = .2f),
            modifier = Modifier
                .padding(vertical = 24.dp)
                .size(60.dp)
        )

        Text(
            text = message,
            color = darkenColor(originalColor = Color.Red, factor = .2f),
            textAlign = TextAlign.Center
        )

        OutlinedButton(
            onClick = onRetryClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            ),
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "Reintentar",
                color = darkenColor(originalColor = Color.Red, factor = .2f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewErrorScreen(){
    ErrorScreen(
        message = "Error al obtener listado de personajes.\nIntentelo de nuevo",
        onRetryClick = {}
    )
}