package plat.rickandmorty.app.splash.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import plat.rickandmorty.app.core.data.PreferencesDataStore

class SplashViewModel(app: Application) : AndroidViewModel(app) {
    private val dataStore = PreferencesDataStore(app.applicationContext)
    suspend fun isLogged(): Boolean = dataStore.getUsername() != ""
}