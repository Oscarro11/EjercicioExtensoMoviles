package plat.rickandmorty.app.login.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.rickandmorty.app.core.data.PreferencesDataStore

sealed interface LoginEvent {
    data class ChangeName(
        val name: String
    ) : LoginEvent
}

class LoginViewModel(app: Application) : AndroidViewModel(app) {
    private val dataStore = PreferencesDataStore(app.applicationContext)
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val savedName = dataStore.getUsername()
            _state.update { it.copy(name = savedName) }
        }
    }

    fun onChange(event: LoginEvent){
        when (event) {
            is LoginEvent.ChangeName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
        }
    }

    fun login(onDone: () -> Unit) {
        viewModelScope.launch {
            dataStore.setUserData(username = _state.value.name)
            onDone()
        }
    }
}