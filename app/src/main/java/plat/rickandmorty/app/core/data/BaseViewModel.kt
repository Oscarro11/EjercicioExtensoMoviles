package plat.rickandmorty.app.core.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration

sealed interface AppEvent{
    data object ClickLoading: AppEvent
    data object ClickRetry: AppEvent
}

abstract class BaseViewModel<T, E>(
    val loadingTime: Duration
) : ViewModel() {
    private val _state = MutableStateFlow(AppState<T>())
    val state = _state.asStateFlow()

    private var loadingJob: Job? = null

    fun onChange(event: AppEvent){
        when (event){
            AppEvent.ClickLoading -> {
                triggerError()
            }

            AppEvent.ClickRetry -> {
                triggerLoading()
            }
        }
    }

    fun onEvent(event: E) = handleEvent(event)

    protected abstract fun handleEvent(event: E)

    protected fun updateState(data: T) {
        _state.update { it.copy(
            data = data
        ) }
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

            loadData()
                .catch {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = null,
                            hasError = true
                        )
                    }
                }
                .collect { data ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = data,
                            hasError = false
                        )
                    }
                }
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

    protected abstract fun loadData(): Flow<T>
}