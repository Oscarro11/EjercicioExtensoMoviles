package plat.rickandmorty.app.core.data

data class AppState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val hasError: Boolean = false
)