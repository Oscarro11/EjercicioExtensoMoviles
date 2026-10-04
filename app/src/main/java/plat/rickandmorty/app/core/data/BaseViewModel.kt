package plat.rickandmorty.app.core.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration

sealed interface Event{
    data object ClickLoading: Event
    data object ClickRetry: Event
}

abstract class BaseViewModel<T>(
    val loadingTime: Duration
) : ViewModel() {
    private val _state = MutableStateFlow(AppState<T>())
    val state = _state.asStateFlow()

    private var loadingJob: Job? = null

    fun onChange(event: Event){
        when (event){
            Event.ClickLoading -> {
                triggerError()
            }

            Event.ClickRetry -> {
                triggerLoading()
            }
        }
    }

    protected fun triggerLoading() {
        loadingJob?.cancel()

        loadingJob = viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                data = null,
                hasError = false
            )

            delay(loadingTime)

            _state.value = _state.value.copy(
                isLoading = false,
                data = loadData(),
                hasError = false
            )
        }
    }

    protected fun triggerError() {
        loadingJob?.cancel()
        loadingJob = null

        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = false,
                data = null,
                hasError = true
            )
        }
    }

    protected abstract suspend fun loadData(): T
}