package plat.rickandmorty.app.chores.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import plat.rickandmorty.app.chores.ui.error.ErrorScreen
import plat.rickandmorty.app.chores.ui.loading.LoadingScreen
import plat.rickandmorty.app.core.data.AppState

@Composable
fun <T> AppStateContent(
    state: AppState<T>,
    errorMessage: String,
    onLoadingClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    val data = state.data

    when {
        state.isLoading -> {
            LoadingScreen(
                modifier = modifier,
                onScreenClick = onLoadingClick
            )
        }

        state.hasError -> {
            ErrorScreen(
                message = errorMessage,
                onRetryClick = onRetryClick,
                modifier = modifier
            )
        }

        data != null -> {
            content(data)
        }
    }
}