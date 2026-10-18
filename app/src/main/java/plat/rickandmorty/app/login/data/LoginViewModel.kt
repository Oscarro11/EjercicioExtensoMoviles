package plat.rickandmorty.app.login.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.rickandmorty.app.character.data.CharacterDb
import plat.rickandmorty.app.character.data.mapToEntity
import plat.rickandmorty.app.core.Dependencies
import plat.rickandmorty.app.core.data.PreferencesDataStore
import plat.rickandmorty.app.location.data.LocationDb
import plat.rickandmorty.app.location.data.mapToEntity
import kotlin.time.Duration.Companion.seconds

sealed interface LoginEvent {
    data class ChangeName(
        val name: String
    ) : LoginEvent
}

class LoginViewModel(private val app: Application) : AndroidViewModel(app) {
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
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            if (dataStore.getUsername() == "") {
                Dependencies.database.characterDAO().insertCharacter(
                    *CharacterDb.getAllCharacters().map {
                            character -> character.mapToEntity()
                    }.toTypedArray()
                )

                Dependencies.database.locationDAO().insertLocation(
                    *LocationDb.getAllLocations().map {
                            location -> location.mapToEntity()
                    }.toTypedArray()
                )
            }

            dataStore.setUsername(username = _state.value.name)
            delay(4.seconds)

            onDone()
        }
    }
}