package plat.rickandmorty.app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import plat.rickandmorty.app.core.Dependencies
import plat.rickandmorty.app.core.navigation.AppNavHost
import plat.rickandmorty.app.core.ui.theme.RickAndMortyTheme

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Dependencies.buildDatabase(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                AppNavHost()
            }
        }
    }
}