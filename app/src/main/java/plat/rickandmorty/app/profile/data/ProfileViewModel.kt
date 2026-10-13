package plat.rickandmorty.app.profile.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import plat.rickandmorty.app.core.data.PreferencesDataStore

class ProfileViewModel(app: Application) : AndroidViewModel(app) {
    private val dataStore = PreferencesDataStore(app.applicationContext)
    fun getUsername(): String {
        var name = ""

        viewModelScope.launch {
            name = dataStore.getUsername()
        }

        return name
    }
    fun logout(onDone: () -> Unit) {
        viewModelScope.launch {
            dataStore.setUserData(username = "")
            onDone()
        }
    }
}